package com.example.cheicksa.navigation.navgraphs.restaurant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.bottomBar.BottomBarNavigation
import com.example.cheicksa.presentation.HomeScreen
import com.example.cheicksa.presentation.restaurantcreens.Cart

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.restaurantBottomNav(
    navController: NavController,
) {
    navigation(
        startDestination = BottomBarNavigation.Home.route,
        route = NavConstants.RESTAURANT_NAV_BAR
    ) {
        restaurantNav(navController)
        composable(
            route = BottomBarNavigation.Cart.route
        ) {
            Cart(navController = navController)
        }

        composable(
            route = BottomBarNavigation.Favorites.route
        ) {
           // Bills(navController = navController)
        }
        composable(
            route = BottomBarNavigation.Profile.route
        ) {
            //Profile(navController = navController)
        }
    }
}