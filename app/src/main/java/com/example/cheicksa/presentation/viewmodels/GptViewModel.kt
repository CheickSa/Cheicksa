package com.example.cheicksa.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheicksa.model.gpt.request.ChatRequest
import com.example.cheicksa.model.gpt.request.Message
import com.example.cheicksa.model.gpt.request.Function
import com.example.cheicksa.model.gpt.request.Location
import com.example.cheicksa.model.gpt.request.Parameters
import com.example.cheicksa.model.gpt.request.Properties
import com.example.cheicksa.model.gpt.request.Tool
import com.example.cheicksa.model.gpt.request.Unit
import com.example.cheicksa.model.gpt.response.ChatResponse
import com.example.cheicksa.remote.GptApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GptViewModel @Inject constructor(
    private val gptApi: GptApi
): ViewModel() {

    private val _request = MutableStateFlow<ChatRequest?>(null)
    val request = _request.asStateFlow()

    private val model = "gpt-3.5-turbo"
    private val toolsType = "function"
    private val types = "String"

    fun setRequest() {
        val message = Message(
            role = "user" ,
            content = "What is the weather like in Boston?" ,
        )
        val location = Location(
            type = types,
            description = "The city and state, e.g. San Francisco, CA",
        )
        val unit = Unit(
            type = types ,
            enum = listOf("celsius","fahrenheit"),
        )
        val property = Properties(
            location = location,
            unit = unit,
        )
        val parameter = Parameters(
            type = "object",
            properties = property,
            required = listOf("location") ,
        )
        val function = Function(
            name = "get_current_weather",
            description = "Get the current weather in a given location",
            parameters = parameter,
        )
        val tool = Tool(
            type = toolsType,
            function = function ,
        )

        val request = ChatRequest(
            model = model,
            messages = listOf(message) ,
            tools = listOf(tool),
            toolChoice = "auto"
        )
        _request.value = request
        viewModelScope.launch {
            getResponse(request)
        }
    }

    private val _response = MutableStateFlow<ChatResponse?>(null)
    val response = _response.asStateFlow()

    private suspend fun getResponse(response: ChatRequest) {
        val apiResponse = gptApi.getGptResponse(response)
        viewModelScope.launch {
            if (apiResponse.isSuccessful) _response.value = apiResponse.body()
            else _response.value = null
        }
    }

}