package com.example.cheicksa.presentation.restaurantcreens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.MealsList
import com.example.cheicksa.model.restaurant.OrderScreenCardData
import com.example.cheicksa.model.restaurant.RestaurantData
import com.example.cheicksa.navigation.RestaurantScreens
import com.example.cheicksa.presentation.DiscountBar
import com.example.cheicksa.presentation.common_ui.restaurant.OrderContainer
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantInfoContainer
import com.example.cheicksa.presentation.lesRestaurants
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import kotlinx.coroutines.launch

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

val orderScreenCardData = OrderScreenCardData(
    title = "Cheeseburger",
    description = "Cheeseburger + Sosis + Frites + Boisson",
    price = 300.0,
    imageUlr = "https://www.mcdonalds.com/is/image/content/dam/usa/nfl/nutrition/items/hero/desktop/t-mcdonalds-Cheeseburger.jpg",
    type = "Plat",
)

val mealsList = listOf(
    MealsList(
        mealTitle = "Populaire",
        cards = listOf(
            orderScreenCardData,
            orderScreenCardData,
            orderScreenCardData,
        ),
    ),
    MealsList(
        mealTitle = "Plats",
        cards = listOf(
            orderScreenCardData,
            orderScreenCardData,
            orderScreenCardData,
        ),
    ),
    MealsList(
        mealTitle = "Boissons",
        cards = listOf(
            orderScreenCardData,
            orderScreenCardData,
            orderScreenCardData,
        ),
    ),
    MealsList(
        mealTitle = "Menu",
        cards = listOf(
            orderScreenCardData,
            orderScreenCardData,
            orderScreenCardData,
        ),
    )

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity),
) {
    val state = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var position by rememberSaveable {
        mutableStateOf(0)
    }
    val restaurant = remember { menuViewModel.restaurant.value }
    Scaffold(
        topBar = {
            Column (
                modifier = Modifier
                    .height(if (state.isScrolled) 0.dp else 270.dp)
            ){
                RestaurantInfo(restaurant = restaurant)
                Spacer(modifier = Modifier.height(15.dp))
                DiscountBar()
            }
        }
    ) {
        Column {
            Spacer(modifier = Modifier.height(15.dp))
            LazyRow(
                contentPadding = it,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp, end = 20.dp)
            ) {
                item {
                    restaurant.mealsList.forEachIndexed { ind, meal ->
                        Meals(meal.mealTitle, onClick = {
                            coroutineScope.launch {
                                state.animateScrollToItem(ind)
                                position = ind
                            }
                        })
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }
            LazyColumn(
                state = state,
            ) {
                // les nourritures du restaurant
                val restaurants = restaurant.mealsList
                items(restaurants.size) {mealId->
                    OrderCards(
                        data=restaurants[mealId],
                        navController = navController,
                        menuViewModel = menuViewModel
                    )
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Meals(
    text: String = "text1",
    onClick: () -> Unit = {}
) {
    var color by rememberSaveable { mutableStateOf(false) }
    OutlinedCard(
        modifier = Modifier,
        onClick = {
            color = !color
            onClick.invoke()
        },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(Color.Unspecified)
    ) {
        Text(
            text = text,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 5.dp, start = 8.dp, end = 8.dp, bottom = 5.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OrderCards(
    data: MealsList,
    navController: NavController,
    menuViewModel: MenuViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = data.mealTitle,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        )
        data.cards.forEachIndexed { _, it->
            OrderContainer(
                foodName = it.title,
                ingredient = it.description,
                price = it.price,
                image = it.imageUlr
            ) {
                menuViewModel.setOrder(it)
                navController.navigate(RestaurantScreens.Ordering.route)
            }
        }
    }
}

@Composable
fun RestaurantInfo(
    restaurant: RestaurantData
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        // restaurantName
        Text(
            text = restaurant.name,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
        )
        // delivery fee
        RestaurantInfoContainer(
            color = Color.Green,
            painter = painterResource(id = R.drawable.time),
            details = if (restaurant.deliveryFee==0.0) stringResource(R.string.free_delivery) else
                stringResource(id = R.string.delivery_fee,restaurant.deliveryFee)+
                stringResource(id = R.string.devise),
            additionalDetail = "",
            onClick = {}
        )
        // delivery time
        RestaurantInfoContainer(
            painter = painterResource(id = R.drawable.time),
            details = stringResource(R.string.delivery_time, restaurant.deliveryTime),
            additionalDetail = "",
            onClick = {}
        )
        // min order
        RestaurantInfoContainer(
            painter = painterResource(id = R.drawable.money),
            details = stringResource(R.string.mim_order, restaurant.minOrder) +
                    stringResource(id = R.string.devise),
            additionalDetail = "",
            onClick = {}
        )
        // rate
        RestaurantInfoContainer(
            painter = painterResource(id = R.drawable.star),
            details = restaurant.rate.toString(),
            additionalDetail = stringResource(R.string.rater_count,restaurant.raterCount),
            onClick = {}
        )
    }
}



@Preview
@Composable
fun OrderScreen_() {
    CheicksaTheme{
        OrderScreen(rememberNavController())

    }
}