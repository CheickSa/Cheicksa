package com.example.cheicksa.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cheicksa.navigation.AuthScreens
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.presentation.authentification.LogIn
import com.example.cheicksa.presentation.authentification.SignIn

fun NavGraphBuilder.authNav(
    navController: NavController
){
    navigation(
        startDestination = AuthScreens.Login.route,
        route = NavConstants.AUTH_ROUTE
    ){
        composable(
            route = AuthScreens.Login.route
        ){
           LogIn(navController)
        }
        composable(
            route = AuthScreens.Register.route
        ){
            SignIn(navController)
        }
    }
}