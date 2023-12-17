package com.example.cheicksa.presentation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.Club
import com.example.cheicksa.model.restaurant.Cuisine
import com.example.cheicksa.model.restaurant.MealsList
import com.example.cheicksa.model.restaurant.OrderScreenCardData
import com.example.cheicksa.model.restaurant.RestaurantData
import com.example.cheicksa.navigation.GptScreens
import com.example.cheicksa.navigation.RestaurantScreens
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.common_ui.restaurant.CuisineContainer
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantContainer
import com.example.cheicksa.presentation.common_ui.restaurant.SearchBar
import com.example.cheicksa.presentation.common_ui.restaurant.StoreContainer
import com.example.cheicksa.presentation.restaurantcreens.mealsList
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme

@Composable
fun HomeScreen(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel()
) {
    val restaurants by menuViewModel.restaurants.collectAsState()


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(bottom = 20.dp)
    ){
        item { SearchBar(
            onClick = {

                navController.navigate(GptScreens.Gpt.route)
            },
            text = stringResource(R.string.search_bar_text)
        ) }
        item { Stores(navController) }
        item { DiscountBar() }
        item { Cuisines(navController=navController) }
        item { Restaurant(
            navController=navController,
            restaurants = restaurants
        ) }
        item { Restaurant(navController=navController) }
    }
}



@Composable
fun Stores(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(295.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Row (
            modifier = Modifier
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                StoreContainer(
                    width = 165.dp,
                    height = 126.dp,
                    imageModifier = Modifier
                        .width(127.dp)
                        .height(111.dp)
                        .offset(y = 30.dp, x = 45.dp),
                    title = stringResource(id = R.string.superMarket),
                    painter = painterResource(id = R.drawable.sup_market),
                    onClick = {navController.navigate(StoreScreens.SuperMarche.route)},
                )
                Spacer(modifier = Modifier.height(10.dp))
                StoreContainer(
                    width = 165.dp,
                    height = 126.dp,
                    painter = painterResource(id = R.drawable.boutique),
                    imageModifier = Modifier
                        .width(127.dp)
                        .height(111.dp)
                        .offset(y = 25.dp, x = 50.dp),
                    title =  stringResource(id = R.string.boutique),
                    onClick = {navController.navigate(StoreScreens.Boutique.route)}
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column (
                modifier = Modifier
                    .weight(1f)
            ){
                StoreContainer(
                    width = 165.dp,
                    height = 175.dp,
                    painter = painterResource(id = R.drawable.resto),
                    imageModifier = Modifier
                        .width(215.dp)
                        .height(165.dp)
                        .rotate(-13f)
                        .offset(y = 10.dp, x = 20.dp),
                    title =  stringResource(id = R.string.restaurant),
                    onClick = {navController.navigate(StoreScreens.Restaurant.route)}
                )
                Spacer(modifier = Modifier.height(10.dp))
                StoreContainer(
                    width = 165.dp,
                    height = 75.dp,
                    painter = painterResource(id = R.drawable.cloth),
                    imageModifier = Modifier
                        .fillMaxSize(),
                    title =  stringResource(id = R.string.cloths),
                    onClick = {navController.navigate(StoreScreens.Vetement.route)}
                )
            }
        }
    }
}

@Composable
fun DiscountBar(
    club: Club = Club()
) {
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(65.dp),
        shape = MaterialTheme.shapes.medium,
    ){
        Column (
            modifier = Modifier
                .padding(top = 5.dp, start = 8.dp)
        ){
            Text(
                text = club.title,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = club.description,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
        }
    }
    
}



val cuisinesList = listOf(
    Cuisine(title = "Pizza", ),
    Cuisine(title = "Burger", ),
    Cuisine(title = "Dessert", ),
    Cuisine(title = "Petit Dejeuner",),
)

@Composable
fun Cuisines(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity)

) {
    val cuisines by menuViewModel.cuisines.collectAsState()
    val cuisineLoding by remember { menuViewModel.cuisineLoading }

    Column {
        Text(
            text = stringResource(id = R.string.cuisines),
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (cuisines.isEmpty()){
                items(4){
                    CuisineContainer()
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
            items(cuisines.size) {
                val cuisine = cuisines[it]
                CuisineContainer(
                    imageUrl = cuisine.imageUrl,
                    title = cuisine.title,
                    onClick = { navController.navigate(RestaurantScreens.Cuisine.route) },
                    loading = cuisineLoding
                )
                Log.d("a", cuisineLoding.toString())
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
    
}



val lesRestaurants = listOf(
    RestaurantData(
        id = 2L,
        //image = R.drawable.burger,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/cheicksa-81df4.appspot.com/o/cuisines%2FPetit%20Dej.jpeg?alt=media&token=652b50f9-a4ab-42f0-9171-572cfe84462e",
        name = "Burger Star",
        category = "Burger",
        deliveryFee = 30.0,
        minOrder = 50.0,
        mealsList = listOf(
            MealsList(
                mealTitle = "Populaire",
                cards = listOf(
                    OrderScreenCardData(
                        type = "Burger",
                        title = "Good Burger",
                        description = "Burger + Cheese + Frite + Boisson",
                        price = 200.0,
                    )
                )
            )
        )+ mealsList,
        foodEmoji = "\uD83C\uDF54"
    ),
    RestaurantData(
        id = 3L,
        //image = R.drawable.dessert,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/cheicksa-81df4.appspot.com/o/cuisines%2FBurger.webp?alt=media&token=e449eeac-6620-45b2-bc4c-0caad7f5b362",
        name = "Dessert King",
        category = "Dessert",
        mealsList = mealsList,
        deliveryFee = 30.0,
        minOrder = 50.0,
        foodEmoji = "\uD83C\uDF70"
    ),

)

@Composable
fun Restaurant(
    title: String = "Restaurant",
    restaurants: List<RestaurantData> = lesRestaurants,
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity)
) {
    val loading by remember { menuViewModel.loading }
    Column {
        Text(
            text = title,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (restaurants.isEmpty()){
                items(3){
                    RestaurantContainer(
                        loading = true
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
            items(restaurants.size) {
                val restaurant = restaurants[it]
                RestaurantContainer(
                    image = restaurant.imageUrl,
                    name = restaurant.name,
                    category = restaurant.category,
                    mimOrder = restaurant.minOrder,
                    deliveryFee = restaurant.deliveryFee,
                    onClick = {
                        menuViewModel.setRestaurant(restaurant)
                        navController.navigate(RestaurantScreens.Order.route)
                    },
                    emoji = restaurant.foodEmoji,
                    deliveryTime = restaurant.deliveryTime,
                    isVerified = restaurant.isVerified,
                    loading = loading
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
}



@Preview
@Composable
fun HomeScreen_() {
    CheicksaTheme {
        HomeScreen(rememberNavController())
    }
}