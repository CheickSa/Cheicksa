package com.example.cheicksa.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.RestaurantScreens
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.restaurantcreens.CuisineScreen
import com.example.cheicksa.presentation.restaurantcreens.Menu
import com.example.cheicksa.presentation.restaurantcreens.OrderScreen
import com.example.cheicksa.presentation.restaurantcreens.Ordering


fun NavGraphBuilder.restaurantNav(
    navController: NavController
){
    navigation(
        startDestination = StoreScreens.restaurant,
        route = NavConstants.RESTAURANT_ROUTE
    ){
        composable(
            route = StoreScreens.restaurant,
        ){
            Menu(navController)
        }
        composable(
            route = RestaurantScreens.Cuisine.route ,
        ){
            CuisineScreen(navController=navController)
        }
        composable(
            route = RestaurantScreens.Ordering.route ,
        ){
            Ordering(navController=navController)
        }
        composable(
            route = RestaurantScreens.Order.route,

        ){

            OrderScreen(navController = navController,)

        }
    }
    //TODO("documenting the fuction")
}