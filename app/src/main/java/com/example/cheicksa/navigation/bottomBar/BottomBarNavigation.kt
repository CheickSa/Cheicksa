package com.example.cheicksa.navigation.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.RestaurantScreens

sealed class BottomBarNavigation (
    val route: String,
    val icon: Any? = null,
    val title: String,
    val iconOutlined: Any? = null,
) {
    data object Home : BottomBarNavigation(
        NavConstants.RESTAURANT_ROUTE,
        Icons.Filled.Home,
        "Home",
        Icons.Outlined.Home
    )
    data object Favorites : BottomBarNavigation(
        "favorites_bottom_bar",
        Icons.Filled.Favorite,
        "Favoris",
        Icons.Outlined.FavoriteBorder
    )
    data object Cart : BottomBarNavigation(
        RestaurantScreens.Cart.route,
        Icons.Filled.ShoppingCart,
        "Cart",
        Icons.Outlined.ShoppingCart
    )
    data object Profile : BottomBarNavigation(
        "profile_bottom_bar",
        Icons.Filled.Person,
        "profile",
        Icons.Outlined.Person
    )
}
