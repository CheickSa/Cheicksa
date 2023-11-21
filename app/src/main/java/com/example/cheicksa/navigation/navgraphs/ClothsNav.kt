package com.example.cheicksa.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.cloths.ClothsHomeSreen
import com.example.cheicksa.presentation.restaurantcreens.Menu

fun NavGraphBuilder.clothsNav(
    navController: NavController
){
    navigation(
        startDestination = StoreScreens.vetement,
        route = NavConstants.CLOTH_ROUTE
    ){
        composable(
            route = StoreScreens.vetement
        ){
            ClothsHomeSreen(navController)
        }
    }
}