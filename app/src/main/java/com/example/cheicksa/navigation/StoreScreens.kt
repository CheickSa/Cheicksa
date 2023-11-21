package com.example.cheicksa.navigation

import androidx.compose.ui.res.stringResource
import com.example.cheicksa.R


sealed class StoreScreens(
    val name: String ,
    val route: String
){
    data object HomeSreen: StoreScreens(
        name = "home",
        route = this.superMarche
    )
    data object SuperMarche: StoreScreens(
        name = "Super Marche",
        route = this.superMarche
    )
    data object Restaurant: StoreScreens(
        name = "Restaurant",
        route = this.restaurant
    )
    data object Boutique: StoreScreens(
        name = "Boutique",
        route = this.boutique
    )
    data object Vetement: StoreScreens(
        name = "Vetement",
        route = this.vetement
    )
    companion object Names {
        const val boutique = "boutique"
        const val restaurant = "restaurant"
        const val superMarche = "superMarche"
        const val vetement = "vetement"
        const val home = "home"
    }
}


