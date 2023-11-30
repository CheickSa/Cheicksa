package com.example.cheicksa.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.Club
import com.example.cheicksa.model.restaurant.Cuisine
import com.example.cheicksa.model.restaurant.MealsList
import com.example.cheicksa.model.restaurant.OrderScreenCardData
import com.example.cheicksa.model.restaurant.OrderScreenData
import com.example.cheicksa.model.restaurant.RestaurantData
import com.example.cheicksa.navigation.RestaurantScreens
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.common_ui.restaurant.CuisineContainer
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantContainer
import com.example.cheicksa.presentation.common_ui.restaurant.SearchBar
import com.example.cheicksa.presentation.common_ui.restaurant.StoreContainer
import com.example.cheicksa.presentation.restaurantcreens.mealsList
import com.example.cheicksa.ui.theme.CheicksaTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(bottom = 20.dp)
    ){
        item { SearchBar(
            onClick = {},
            text = stringResource(R.string.search_bar_text)
        ) }
        item { Stores(navController) }
        item { DiscountBar() }
        item { Cuisines(navController=navController) }
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
    Cuisine(category = "Pizza", R.drawable.pizza, ),
    Cuisine(category = "Burger", R.drawable.burger,),
    Cuisine(category = "Dessert", R.drawable.dessert,),
    Cuisine(category = "Petit Dejeuner", R.drawable.petit_dej),
)

@Composable
fun Cuisines(
    cuisines: List<Cuisine> = cuisinesList,
    navController: NavController
) {
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
            items(cuisines.size) {
                val cuisine = cuisines[it]
                CuisineContainer(
                    painter = painterResource(cuisine.image),
                    title = cuisine.category,
                    onClick = { navController.navigate(RestaurantScreens.Cuisine.route +"/"+ cuisine.category) }
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
    
}



val lesRestaurants = listOf(
    RestaurantData(
        id = 1L,
        image = R.drawable.pizza,
        name = "Pizza Bulls",
        category = "Pizza",
        deliveryFee = 30.0,
        minOrder = 50.0,
        mealsList = listOf(
            MealsList(
                mealTitle = "Populaire",
                orderScreenDatas = OrderScreenData(
                    title = "Populaires",
                    cards = listOf(
                        OrderScreenCardData(
                            title = "Good Pizza",
                            description = "Pizza + Cheese + Frite + Boisson",
                            price = 300.0,
                            painter = R.drawable.pizza
                        )
                    )
                )
            )
        ) + mealsList,
        foodEmoji = "\uD83C\uDF55"
    ),
    RestaurantData(
        id = 2L,
        image = R.drawable.burger,
        name = "Burger Star",
        category = "Burger",
        deliveryFee = 30.0,
        minOrder = 50.0,
        mealsList = listOf(
            MealsList(
                mealTitle = "Populaire",
                orderScreenDatas = OrderScreenData(
                    title = "Populaires",
                    cards = listOf(
                        OrderScreenCardData(
                            title = "Good Burger",
                            description = "Burger + Cheese + Frite + Boisson",
                            price = 200.0,
                            painter = R.drawable.burger
                        )
                    )
                )
            )
        )+ mealsList,
        foodEmoji = "\uD83C\uDF54"
    ),
    RestaurantData(
        id = 3L,
        image = R.drawable.dessert,
        name = "Dessert King",
        category = "Dessert",
        mealsList = mealsList,
        deliveryFee = 30.0,
        minOrder = 50.0,
        foodEmoji = "\uD83C\uDF70"
    ),
    RestaurantData(
        id = 4L,
        image = R.drawable.petit_dej,
        name = "Petit Dejeuner",
        category = "Petit Dejeuner",
        mealsList = mealsList,
        deliveryFee = 30.0,
        minOrder = 50.0,
        foodEmoji = "\uD83E\uDD50"
    ),
    RestaurantData(
        id = 1L,
        image = R.drawable.pizza,
        name = "Pizza Bulls",
        category = "Pizza",
        mealsList = mealsList,
        deliveryFee = 30.0,
        minOrder = 50.0,
    ),
    RestaurantData(
        id = 2L,
        image = R.drawable.burger,
        name = "Burger Star",
        category = "Burger",
        mealsList = mealsList,
        deliveryFee = 30.0,
        minOrder = 50.0,
    ),
    RestaurantData(
        id = 3L,
        image = R.drawable.dessert,
        name = "Dessert King",
        category = "Dessert",
        mealsList = mealsList,
        deliveryFee = 30.0,
        minOrder = 50.0,
    ),
    RestaurantData(
        id = 4L,
        image = R.drawable.petit_dej,
        name = "Petit Dejeuner",
        category = "Petit Dejeuner",
        mealsList = mealsList,
        deliveryFee = 30.0,
        minOrder = 50.0,
    )
)

@Composable
fun Restaurant(
    title: String = "Restaurant",
    restaurants: List<RestaurantData> = lesRestaurants,
    navController: NavController
) {
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
            items(restaurants.size) {
                val restaurant = restaurants[it]
                RestaurantContainer(
                    painter = painterResource(restaurant.image),
                    name = restaurant.name,
                    category = restaurant.category,
                    mimOrder = restaurant.minOrder,
                    deliveryFee = restaurant.deliveryFee,
                    navController = navController ,
                    id = restaurant.id,
                    emoji = restaurant.foodEmoji
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