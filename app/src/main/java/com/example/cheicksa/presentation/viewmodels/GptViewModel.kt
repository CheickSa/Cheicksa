package com.example.cheicksa.presentation.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.cheicksa.AppDatabase
import com.example.cheicksa.model.gpt.Chat
import com.example.cheicksa.model.gpt.request.ChatRequest
import com.example.cheicksa.model.gpt.request.Message
import com.example.cheicksa.model.gpt.request.Tool
import com.example.cheicksa.model.gpt.response.ChatResponse
import com.example.cheicksa.model.gpt.response.ResponseObject
import com.example.cheicksa.model.gpt.response.ToolCall
import com.example.cheicksa.model.restaurant.RestaurantData
import com.example.cheicksa.presentation.lesRestaurants
import com.example.cheicksa.remote.GptApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class GptViewModel @Inject constructor(
    private val gptApi: GptApi,
): ViewModel() {
    private val model = "gpt-3.5-turbo"
    private val toolsType = "function"
    private val types = "String"

    private val userId = Firebase.auth.currentUser?.uid
    private val db = Firebase.firestore
    private val chatRef = db.collection("chats")
        .document("users")
        .collection("chats")

    private val menuViewModel = MenuViewModel()

    private val _chats = mutableStateOf<List<Chat>>(emptyList())
    val chats: State<List<Chat>> = _chats

    private val _thinking = mutableStateOf(false)
    val thinking: State<Boolean> = _thinking

    private val _restaurantsList = MutableStateFlow<List<List<RestaurantData>?>>(emptyList())
    val restaurantsList = _restaurantsList.asStateFlow()


    private val _response = MutableStateFlow<ChatResponse?>(null)
    val response = _response.asStateFlow()

    private val _toolData = MutableStateFlow<String?>(null)
    val toolData = _toolData.asStateFlow()

    private val _restaurants =  MutableStateFlow<List<RestaurantData>?>(null)

    val restaurants = _restaurants.asStateFlow()


    private fun userMessage(message: String): String {
        return """
            {
              "role": "user",
              "content": "$message"
            }
        """.trimIndent()
    }
    private fun assistantResponse(response: String): String {
        return """
            {
              "role": "assistant",
              "content": "$response"
            }
        """.trimIndent()
    }


    fun setRequest(message: String) {
        _chats.value += Chat(message = message, role = "user")



        // adapt the conversation  the chat gpt api understands and add to the request
        var conversation = ""
        // the first chunk is the user message and the assistant response so we need to separate them
        _chats.value.chunked(2).map {
            conversation += if (it.size == 2) {
                ","+userMessage(it[0].message) + "," + assistantResponse(it[1].message) + ","
            } else {
                "," + userMessage(it[0].message)
            }
        }
        Log.d("TAG", "setRequest: $conversation")

        val a = menuViewModel.restaurants.value.map { it.name + "meals are" + it.mealsList.map { it.cards } }.toString()
        Log.d("TAG", "setRequest: $a")

        val content = "You are a friendly and helpful assistant, skilled in helping people to place" +
                " their order. you don't ask personal address, location or payment information. " +
                "You can Make suggestions and recommendations if the user don't have any idea about what to order." +
                "you can answer any question even if it's not related to the order. " +
                "You know the list of restaurants and their menus. " +
                menuViewModel.restaurants.value.map { "name " + it.name + " menu are" + it.mealsList.map { it.cards } }.toString() +
                "You know the list of cuisines" +
                menuViewModel.cuisines.value.map { it.title }.toString() +
                "If user ask for non existing restaurant you can tell him that the restaurant is not available"




        val request = """
            {
              "model": "gpt-4",
              "messages": [
                {
                  "role": "system", 
                  "content": "$content"
                }
                ${conversation.replace(",,",",")}
              ],
              "tools": [
                {
                  "type": "function",
                  "function": {
                    "name": "order_food",
                    "description": "Get the current weather in a given location",
                    "parameters": {
                      "type": "object",
                      "properties": {
                        "restaurantName": {
                          "type": "string",
                          "description": "The restaurant name, e.g. Burger King"
                        },
                        "foodName": {
                          "type": "string",
                          "description": "The food name, e.g. cheeseburger"
                        },
                        "foodCategory": {
                          "type": "string",
                          "description": "The food category, e.g. Burger"
                        },
                        "foodQuantity": {
                          "type": "string",
                          "description": "The food quantity, e.g. 2"
                        },
                        "mealId": {
                          "type": "string",
                          "description": "The id of the meal the user selected, e.g. 1"
                        }
                      },
                      "required": ["foodName", "foodCategory", "foodQuantity", "foodPrice"]
                    }
                  }
                }
              ],
              "tool_choice": "auto"
            }
        """.trimIndent()

        _thinking.value = true
        val json = Json.decodeFromString<ChatRequest>(request)
        viewModelScope.launch {
            getResponse(json)

        }
    }







    val error = mutableStateOf("")

    private suspend fun getResponse(response: ChatRequest) {
       // Log.d("TAG", "getResponse: $response")
        val restaurants = menuViewModel.restaurants.value
        val apiResponse = gptApi.getGptResponse(response)
        if (apiResponse.isSuccessful) {
            _response.value = apiResponse.body()
            val message = _response.value?.choices?.first()?.message?.content
            val toolData = _response.value?.choices?.first()?.message?.tool_calls?.get(0)?.function?.arguments
            _toolData.value = toolData


            _thinking.value = false

            if (_toolData.value.isNullOrEmpty()) {
                _chats.value += Chat(message = message.toString(), role = "assistant", restaurantData = null)
            }
            else {
                val responseObject = Json.decodeFromString<ResponseObject>(_toolData.value!!)
                // the exact restaurant
                val responseRestau1 =  restaurants.filter {resto->
                    resto.name == responseObject.restaurantName
                }
                // the restaurant with the same cuisine
                val responseRestau2 =  restaurants.filter {resto->
                    resto.category == responseObject.foodCategory
                }
                val responseRestau = responseRestau1 + responseRestau2
                // the exact meal
                val meals1 = responseRestau.map { it.mealsList.map { meal ->
                    meal.cards.filter { card ->
                        card.id == responseObject.mealId
                    }
                  }
                }.flatten().flatten()

                // the meal with the same food name
                val meals2 = responseRestau.map { it.mealsList.map { meal ->
                    meal.cards.filter { card ->
                        card.title == responseObject.foodName
                    }
                }
                }.flatten().flatten()

                val meals = meals1 + meals2
                Log.d("rep", "getResponse: $meals")

                _chats.value += Chat(message = message.toString(), role = "assistant", restaurantData = responseRestau, meals = meals)
            }
        }
        else {
            _response.value = null
            _thinking.value = false
            error.value = apiResponse.errorBody().toString() + apiResponse.code().toString()
            Log.d("gpt", "getResponse err: ${apiResponse.message() + apiResponse.code().toString()}")
        }
    }

    fun updateChatList(chat: List<Chat>) {
        _chats.value += chat
    }





}