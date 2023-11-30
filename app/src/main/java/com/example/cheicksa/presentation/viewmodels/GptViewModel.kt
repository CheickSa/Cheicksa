package com.example.cheicksa.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheicksa.model.gpt.request.ChatRequest
import com.example.cheicksa.model.gpt.response.ChatResponse
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
    init {
        setRequest()
    }

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
    fun setRequest() {
        val message = "hello, can i buy a cheeseburger?"
        val response = "yes, of course. how many cheeseburgers do you want?"

        val request = """
            {
              "model": "gpt-3.5-turbo",
              "messages": [
                {
                  "role": "system", 
                  "content": "You are a friendly and helpful assistant, skilled in helping people to place their order."
                },
                ${userMessage(message)},
                ${assistantResponse(response)},
                {
                  "role": "user",
                  "content": "just one please"
                }
              ],
              "tools": [
                {
                  "type": "function",
                  "function": {
                    "name": "get_current_weather",
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
                          "enum": ["100", "200", "300"]
                        }
                      },
                      "required": [ "foodName", "foodCategory", "foodQuantity", "foodPrice"]
                    }
                  }
                }
              ],
              "tool_choice": "auto"
            }
        """.trimIndent()

        val json = Json.decodeFromString<ChatRequest>(request)
        viewModelScope.launch {
            getResponse(json)
        }
    }

    private val _response = MutableStateFlow<ChatResponse?>(null)
    val response = _response.asStateFlow()

    val message = _response.value?.choices?.first()?.message?.content
    val toolData = _response.value?.choices?.first()?.message?.tool_calls



    val error = mutableStateOf("")

    private suspend fun getResponse(response: ChatRequest) {
        val apiResponse = gptApi.getGptResponse(response)
        viewModelScope.launch {
            if (apiResponse.isSuccessful) {
                _response.value = apiResponse.body()
            }
            else {
                _response.value = null
                error.value = apiResponse.errorBody().toString() + apiResponse.code().toString()
            }
        }
    }

}