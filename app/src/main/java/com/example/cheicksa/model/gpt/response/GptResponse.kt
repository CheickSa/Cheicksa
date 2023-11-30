package com.example.cheicksa.model.gpt.response

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

@Serializable
data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String
)
@Serializable
data class Message(
    val role: String,
    val content: String?, // Change the type if content can be something other than String
    val tool_calls: List<ToolCall>
)
@Serializable
data class ToolCall(
    val id: String,
    val type: String,
    val function: ToolFunction
)
@Serializable
data class ToolFunction(
    val name: String,
    val arguments: String
)
@Serializable
data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)
