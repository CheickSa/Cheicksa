package com.example.cheicksa.navigation

sealed class RestaurantScreens(
    val name: String ,
    val route: String
) {
    data object Cuisine : RestaurantScreens(
        name = "Cuisine",
        route = "Cuisine"
    )

    data object Menu : RestaurantScreens(
        name = "Menu",
        route = "Menu"
    )
    data object Ordering : RestaurantScreens(
        name = "Ordering",
        route = "Ordering"
    )
    data object Order : RestaurantScreens(
        name = "Order",
        route = "Order"
    )
}
