package com.example.cheicksa.presentation.restaurantcreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.presentation.Cuisines
import com.example.cheicksa.presentation.Restaurant
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantContainer
import com.example.cheicksa.presentation.common_ui.restaurant.SearchBar
import com.example.cheicksa.presentation.common_ui.restaurant.StoreContainer
import com.example.cheicksa.presentation.lesRestaurants
import com.example.cheicksa.ui.theme.CheicksaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navController: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(title = {
                SearchBar(
                    height = 44.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = {},
                    text = "Chercher un restaurant ou une nourriture",
                    description = ""
                )
            },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            )
        }
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(bottom = 20.dp),
            contentPadding = it
        ){
            item { Pub()  }
            item { Cuisines(navController= navController) }
            item { Restaurant(title = "Acheter 1 gagner 1", navController = navController) }
            item { Restaurant(title = "Reduction 50%", navController = navController) }
            item { Restaurant(title = "Restaurant Populaire", navController = navController) }
            item { Text(
                text = "Tous les Restaurants",
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            ) }
            val lesRestaurant = lesRestaurants
            items(lesRestaurant.size){
                val restaurant = lesRestaurant[it]
                RestaurantContainer(
                    modifier = Modifier
                        .height(175.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    textModifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    navController = navController,
                    name = restaurant.name,
                    id = restaurant.id,
                    mimOrder = restaurant.minOrder,
                    deliveryFee = restaurant.deliveryFee,
                    category = restaurant.category,
                    painter = painterResource(id = restaurant.image)
                )
            }
        }
    }
}
val pubsdatas = listOf(
    PubData(name = "Burger King",),
    PubData(name = "Pizza Hut",),
    PubData(name = "Kepap ",)
)

@Composable
fun Pub(
    pubs: List<PubData> = pubsdatas
) {
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(top = 20.dp, start = 20.dp,end=20.dp)
    ){
        items(pubs.size){
            val pub = pubs[it]
            StoreContainer(
                width = 125.dp,
                height = 160.dp,
                painter = painterResource(id = pub.image),
                imageModifier = Modifier
                    .fillMaxSize(),
                title =  pub.name
            )

        }

    }
}


data class PubData(
    val name: String,
    val image: Int = R.drawable.boutique
)

@Preview
@Composable
fun Menu_() {
    CheicksaTheme {
        Menu(rememberNavController())
    }
}