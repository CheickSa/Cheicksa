package com.example.cheicksa.navigation

sealed class AuthScreens(
    val name: String ,
    val route: String
) {
    data object Login : AuthScreens(
        name = "Login",
        route = "Login"
    )
    data object Register : AuthScreens(
        name = "Register",
        route = "Register"
    )
}