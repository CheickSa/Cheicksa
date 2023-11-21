package com.example.cheicksa.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.HomeScreen

fun NavGraphBuilder.homeNav(
    navController: NavController
){
    navigation(
        startDestination = StoreScreens.home,
        route = NavConstants.HOME_SCREEN_ROUTE
    ){
        composable(
            route = StoreScreens.home
        ){
            HomeScreen(navController)
        }
    }
}