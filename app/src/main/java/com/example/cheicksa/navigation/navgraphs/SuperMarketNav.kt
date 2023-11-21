package com.example.cheicksa.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.boutique.BoutiqueHome

fun NavGraphBuilder.superMarketNav(
    navController: NavController
){
    navigation(
        startDestination = StoreScreens.superMarche,
        route = NavConstants.MARKET_ROUTE
    ){
        composable(
            route = StoreScreens.superMarche
        ){
            BoutiqueHome(navController)
        }
    }
}