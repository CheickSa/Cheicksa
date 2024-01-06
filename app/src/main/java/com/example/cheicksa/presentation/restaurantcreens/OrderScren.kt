package com.example.cheicksa.presentation.restaurantcreens

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.MealsList
import com.example.cheicksa.model.restaurant.OrderScreenCardData
import com.example.cheicksa.model.restaurant.RestaurantData
import com.example.cheicksa.navigation.RestaurantScreens
import com.example.cheicksa.presentation.DiscountBar
import com.example.cheicksa.presentation.common_ui.restaurant.OrderContainer
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantInfoContainer
import com.example.cheicksa.presentation.common_ui.restaurant.shimmerEffect
import com.example.cheicksa.presentation.lesRestaurants
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.example.cheicksa.ui.theme.montSarrat
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val LazyListState.isScrolled
    get() = snapshotFlow { this.firstVisibleItemScrollOffset }
        .map { index -> index > 0 }
        .distinctUntilChanged()


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
    Log.d("OrderCards", "OrderCards: ${restaurant}")

    val imageUrl = restaurant.imageUrl



    var showComments by remember { mutableStateOf(false) }
    val stateScroll by remember { state.isScrolled }.collectAsState(false)

    Scaffold(
        topBar = {
            var itemCount = 0
            restaurant.mealsList.forEach {
                it.cards.forEach {
                    itemCount++
                }
            }
            if (stateScroll && itemCount > 2){
                Column (
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiary)
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 700,
                                easing = LinearEasing
                            ),
                        )
                ){
                    TopAppBar(
                        title = {
                            Text(
                                text = restaurant.name,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                fontFamily = montSarrat,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp)
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxSize(),
                                )
                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            bottom = 10.dp,
                            end = 20.dp,
                        )
                    ) {
                        item {
                            restaurant.mealsList.forEachIndexed { ind, meal ->
                                Meals(meal.mealTitle, onClick = {
                                    coroutineScope.launch {
                                        state.animateScrollToItem(ind)
                                        position = ind
                                    }
                                }, color = position == ind)
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                }
            }

        },
    ) {
        LazyColumn(
            state = state,
            contentPadding = it
        ) {
            item {
                var itemCount = 0
                restaurant.mealsList.forEach {
                    it.cards.forEach {
                        itemCount++
                    }
                }
                Box {
                    Column(
                        modifier = Modifier
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 700,
                                    easing = LinearEasing
                                ),
                            )
                            .height(
                                if (stateScroll && itemCount > 2) 0.dp
                                else 200.dp
                            )
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                        ) {
                            val image = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUrl)
                                    .build(),
                            )
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shimmerEffect(
                                        enabled = image.state is AsyncImagePainter.State.Loading,
                                    ),
                                painter = image,
                                contentDescription = "add image",
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                                //error = painterResource(id = R.drawable.resto)
                            )
                        }
                    }
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(10.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(50))
                            .align(Alignment.TopStart)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .align(Alignment.Center)
                            ,
                            tint = Color.Black
                        )
                    }
                }
            }
            item {
                Column {
                    RestaurantInfo(
                        restaurant = restaurant,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    DiscountBar()
                }
            }
            val restaurants = restaurant.mealsList
            items(restaurants.size) {mealId->
                OrderCards(
                    data=restaurants[mealId],
                    navController = navController,
                    menuViewModel = menuViewModel,
                    showcomments = showComments
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Meals(
    text: String = "text1",
    onClick: () -> Unit = {},
    color: Boolean = false
) {
    Button(
        modifier = Modifier
            .height(35.dp),
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = if (color) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.background
        ),
        contentPadding = PaddingValues(2.dp)

    ) {
        Text(
            text = text,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity),
    showcomments: Boolean = false
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
                .padding(start = 10.dp, end = 10.dp),
            fontFamily = montSarrat
        )
        data.cards.forEachIndexed { _, it->
            OrderContainer(
                foodName = it.title,
                ingredient = it.description,
                price = it.price,
                image = it.imageUlr,
                showcomments = showcomments
            ) {
                menuViewModel.setOrder(it)
                navController.navigate(RestaurantScreens.Ordering.route)
            }
            //TODO("add comment like and unlike icon to the card Afer someOne ordered it")
        }
    }
}

@Composable
fun RestaurantInfo(
    restaurant: RestaurantData,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        Text(
            text = restaurant.name,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = montSarrat
        )
        val restaurantInfo = if (restaurant.deliveryFee==0.0) stringResource(R.string.free_delivery) else
            stringResource(id = R.string.delivery_fee,restaurant.deliveryFee).replace(".00","")+
                    stringResource(id = R.string.devise) + " " + stringResource(R.string.delivery_time, restaurant.deliveryTime).replace(".00","") +
                    stringResource(R.string.mim_order, restaurant.minOrder).replace(".00","") +
                    stringResource(id = R.string.devise) + " " + restaurant.rate.toString() + " "
        Text(
            text = restaurantInfo,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = montSarrat
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