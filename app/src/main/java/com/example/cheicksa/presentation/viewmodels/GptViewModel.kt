package com.example.cheicksa.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheicksa.model.gpt.request.ChatRequest
import com.example.cheicksa.model.gpt.response.ChatResponse
import com.example.cheicksa.model.gpt.response.ResponseObject
import com.example.cheicksa.model.gpt.response.ToolCall
import com.example.cheicksa.model.restaurant.RestaurantData
import com.example.cheicksa.presentation.lesRestaurants
import com.example.cheicksa.remote.GptApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class GptViewModel @Inject constructor(
    private val gptApi: GptApi
): ViewModel() {
    private val model = "gpt-3.5-turbo"
    private val toolsType = "function"
    private val types = "String"

    private val _chats = mutableStateOf<List<String>>(emptyList())
    val chats: State<List<String>> = _chats

    private val _thinking = mutableStateOf(false)
    val thinking: State<Boolean> = _thinking

    private val _restaurantsList = MutableStateFlow<List<List<RestaurantData>?>>(emptyList())
    val restaurantsList = _restaurantsList.asStateFlow()

    private fun userMessage(message: String): String {
        return """
            {
              "role": "user",
              "content": "$message"
            }
        """.trimIndent()
    }
    fun assistantResponse(response: String): String {
        return """
            {
              "role": "assistant",
              "content": "$response"
            }
        """.trimIndent()
    }
    fun setRequest(message: String) {
        _chats.value += message

        var conversation = ""
        _chats.value.chunked(2).map {
            conversation += if (it.size == 2) {
                ","+userMessage(it[0]) + "," + assistantResponse(it[1]) + ","
            } else {
                "," + userMessage(it[0])
            }
        }
        Log.d("TAG", "setRequest: $conversation")

        val content = "You are a friendly and helpful assistant, skilled in helping people to place" +
                " their order. you don't ask personal address, location or payment information. " +
                "You can Make suggestions and recommendations if the user don't have any idea about what to order." +
                "you can answer any question even if it's not related to the order. "



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
                        "foodPrice": {
                          "type": "string",
                          "enum": ["200"]
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

    private val _response = MutableStateFlow<ChatResponse?>(null)
    val response = _response.asStateFlow()

    private val _toolData = MutableStateFlow<String?>(null)
    val toolData = _toolData.asStateFlow()

    private val _restaurants =  MutableStateFlow<List<RestaurantData>?>(null)

    val restaurants = _restaurants.asStateFlow()





    val error = mutableStateOf("")

    private suspend fun getResponse(response: ChatRequest) {
        val apiResponse = gptApi.getGptResponse(response)
        viewModelScope.launch {
            if (apiResponse.isSuccessful) {
                _response.value = apiResponse.body()
                val message = _response.value?.choices?.first()?.message?.content
                val toolData = _response.value?.choices?.first()?.message?.tool_calls?.get(0)?.function?.arguments
                _toolData.value = toolData
                _chats.value += message.toString()
                _thinking.value = false
                _restaurantsList.value += if (_toolData.value.isNullOrEmpty()) null
                else {
                    val responseObject = Json.decodeFromString<ResponseObject>(_toolData.value!!)
                    lesRestaurants.filter {resto->
                        //resto.name == responseObject.restaurantName ||
                        resto.category == responseObject.foodCategory
                        // resto.mealsList.any { it.orderScreenDatas.cards.any { it.title.contains(resto.name) } }
                        //resto.name == responseObject.foodQuantity
                    }
                }
            }
            else {
                _response.value = null
                _thinking.value = false
                error.value = apiResponse.errorBody().toString() + apiResponse.code().toString()
            }
        }
    }

}