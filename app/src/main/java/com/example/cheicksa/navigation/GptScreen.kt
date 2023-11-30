package com.example.cheicksa.navigation

sealed class GptScreens(
    val name: String ,
    val route: String
) {
    data object Gpt : GptScreens(
        name = "Gpt",
        route = "Gpt"
    )

}