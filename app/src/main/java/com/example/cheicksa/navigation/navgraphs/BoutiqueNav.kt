package com.example.cheicksa.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.boutique.BoutiqueHome
import com.example.cheicksa.presentation.cloths.ClothsHomeSreen


fun NavGraphBuilder.boutiqueNav(
    navController: NavController
){
    navigation(
        startDestination = StoreScreens.boutique,
        route = NavConstants.BOUTIQUE_ROUTE
    ){
        composable(
            route = StoreScreens.boutique
        ){
            BoutiqueHome(navController)
        }
    }
}