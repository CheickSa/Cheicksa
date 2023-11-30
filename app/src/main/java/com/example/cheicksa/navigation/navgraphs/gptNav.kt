package com.example.cheicksa.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cheicksa.navigation.GptScreens
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.presentation.gpt.ChatScreen


fun NavGraphBuilder.gptNav(
    navController: NavController
){
    navigation(
        startDestination = GptScreens.Gpt.route,
        route = NavConstants.GPT_SCREEN_ROUTE
    ){
        composable(
            route = GptScreens.Gpt.route
        ){
            ChatScreen(navController= navController)
        }
    }
}