package com.example.cheicksa.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.navgraphs.boutiqueNav
import com.example.cheicksa.navigation.navgraphs.clothsNav
import com.example.cheicksa.navigation.navgraphs.homeNav
import com.example.cheicksa.navigation.navgraphs.restaurantNav
import com.example.cheicksa.navigation.navgraphs.superMarketNav

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavConstants.HOME_SCREEN_ROUTE,
        route = NavConstants.ROOT_ROUTE
    ){
        homeNav(navController)
        restaurantNav(navController)
        clothsNav(navController)
        superMarketNav(navController)
        boutiqueNav(navController)
    }
}