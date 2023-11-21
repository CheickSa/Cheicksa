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
            route = RestaurantScreens.Cuisine.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ){
            val cuisineCategory = it.arguments?.getString("id")
            CuisineScreen(cuisineCategory=cuisineCategory,navController=navController)
        }
        composable(
            route = RestaurantScreens.Ordering.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ){
            val argument = it.arguments?.getString("id")
            Ordering(mealInfo=argument, navController=navController)
            //TODO("change the id to long type")
        }
        composable(
            route = RestaurantScreens.Order.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
            })
        ){
            val argument = it.arguments?.getLong("id")
            OrderScreen(navController = navController, restaurantId =argument)

        }
    }
    //TODO("documenting the fuction")
}