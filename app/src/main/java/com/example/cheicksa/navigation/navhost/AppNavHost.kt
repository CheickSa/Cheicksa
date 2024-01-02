package com.example.cheicksa.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.navgraphs.authNav
import com.example.cheicksa.navigation.navgraphs.boutiqueNav
import com.example.cheicksa.navigation.navgraphs.clothsNav
import com.example.cheicksa.navigation.navgraphs.gptNav
import com.example.cheicksa.navigation.navgraphs.homeNav
import com.example.cheicksa.navigation.navgraphs.restaurant.restaurantBottomNav
import com.example.cheicksa.navigation.navgraphs.restaurant.restaurantNav
import com.example.cheicksa.navigation.navgraphs.superMarketNav
import com.example.cheicksa.presentation.viewmodels.FireBaseViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    val fireBaseViewModel = hiltViewModel<FireBaseViewModel>()
    val startDestination = if (fireBaseViewModel.signedIn.value) {
        NavConstants.HOME_SCREEN_ROUTE
    } else {
        NavConstants.AUTH_ROUTE
    }
    NavHost(
        navController = navController,
        startDestination = NavConstants.HOME_SCREEN_ROUTE,
        route = NavConstants.ROOT_ROUTE
    ){
        homeNav(navController)
        //restaurantNav(navController)
        restaurantBottomNav(navController)
        clothsNav(navController)
        superMarketNav(navController)
        boutiqueNav(navController)
        authNav(navController)
        //gptNav(navController)
    }
}