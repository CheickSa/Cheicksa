package com.example.cheicksa.presentation.restaurantcreens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cheicksa.navigation.RestaurantScreens
import com.example.cheicksa.presentation.common_ui.restaurant.RangeSliderExample
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantContainer
import com.example.cheicksa.presentation.common_ui.restaurant.ShowOrderDetails
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.example.cheicksa.ui.theme.montSarrat
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuisineScreen(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity)
) {
    val restaurantsListVm by remember { menuViewModel.restaurants }.collectAsState()
    val cuisine by menuViewModel.cuisine.collectAsState()

    var restaurants by remember { mutableStateOf(restaurantsListVm
        .filter { it.category == cuisine.title })
    }
    var needSuperRestaurant by remember { mutableStateOf(false) }


    Scaffold (
        topBar = {
            Column (

            ) {
                TopAppBar(
                    modifier = Modifier
                        .height(50.dp),
                    title = {
                        Text(
                            text = cuisine.title,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            fontFamily = montSarrat,
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )
                },
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    }
                )
                TopAppBar(
                    modifier = Modifier
                        .height(50.dp),
                    title = {
                    LazyRow (
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 0.dp),

                        ){
                        item {
                            val state = rememberUseCaseState()
                            Filters(
                                filter = "Min Commande",
                                content = {
                                    ShowOrderDetails(
                                        state = state,
                                        positiveText = "OK",
                                        body = {
                                           // val calcMinPrice = restaurants.minOf { it.minOrder }
                                            RangeSliderExample(
                                                startValue = 1000f,
                                                endValue = 100000f,
                                                steps = 0,
                                                onValueChangeFinished = { range ->
                                                    restaurants = restaurantsListVm.filter { it.minOrder in range }
                                                }
                                            )
                                        },
                                        title = "Prix Minimum",
                                    )
                                },
                                onClick = { state.show() },
                                ignoreState = true
                        ) }
                        item {
                            val state = rememberUseCaseState()
                            Filters(
                                filter = "frais Livrson",
                                onClick = { state.show() },
                                content = {
                                    ShowOrderDetails(
                                        state = state,
                                        positiveText = "OK",
                                        body = {
                                            //val calcMinPrice = restaurants.minOf { it.deliveryFee }
                                            RangeSliderExample(
                                                startValue = 0f,
                                                endValue = 10000f,
                                                steps = 0,
                                                onValueChangeFinished = { range ->
                                                    restaurants = restaurantsListVm.filter { it.deliveryFee in range }
                                                }
                                            )
                                        },
                                        title = "Frais de livraison",

                                    )
                                },
                                ignoreState = true

                            ) }
                        item { Filters(
                            filter = "Super Restaurant",
                            onClick = {
                                val newList = restaurantsListVm.filter { it.isVerified && it.category == cuisine.title }
                                restaurants = if (restaurants == newList) {
                                    needSuperRestaurant=false
                                    restaurantsListVm.filter { it.category == cuisine.title }
                                } else {
                                    needSuperRestaurant=true
                                    newList
                                }
                            }
                        ) }
                        item {
                            val state = rememberUseCaseState()
                            Filters(
                                filter = "Temps de livraison",
                                onClick = { state.show() },
                                content = {
                                    ShowOrderDetails(
                                        state = state,
                                        positiveText = "OK",
                                        body = {
                                            val calcMaxPrice = restaurants.maxOf { it.deliveryTime }
                                            RangeSliderExample(
                                                startValue = 0f,
                                                endValue = 120f,
                                                steps = 0,
                                                onValueChangeFinished = { range ->
                                                    restaurants = restaurantsListVm.filter { it.deliveryTime in range }
                                                }
                                            )
                                        },
                                        title = "Temps de livraison",

                                        )
                                },
                                ignoreState = true

                            ) }

                    }
                },
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),

                )
            }
        }
    ){ paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(bottom = 20.dp, top = 20.dp),
            contentPadding = paddingValues
        ){
            //val restaurants = restaurantsList.filter { it.category == cuisine.title }
            items(restaurants.size){
                val restaurant = restaurants[it]
                RestaurantContainer(
                    modifier = Modifier
                        .height(175.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    iconModifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    textModifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    image = restaurant.imageUrl,
                    category = restaurant.category,
                    name = restaurant.name,
                    deliveryFee = restaurant.deliveryFee,
                    mimOrder = restaurant.minOrder,
                    deliveryTime = restaurant.deliveryTime,
                    isVerified = restaurant.isVerified,
                    onClick = {
                        menuViewModel.setRestaurant(restaurant)
                        navController.navigate(RestaurantScreens.Order.route)
                    }
                )
            }

        }
    }

}


@Composable
fun Filters(
    content: @Composable ()->Unit = {},
    onClick: ()->Unit = {},
    filter: String,
    ignoreState: Boolean = false
) {
    var openContent by rememberSaveable {
        mutableStateOf(false)
    }
    val icon = if (ignoreState) Icons.Default.KeyboardArrowDown
    else if (openContent) Icons.Default.KeyboardArrowUp
    else Icons.Default.KeyboardArrowDown
    Column {
        Button(
            modifier = Modifier
                .height(35.dp),
            onClick = {
                openContent = !openContent
                onClick.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.background
            ),
            contentPadding = PaddingValues(2.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = filter,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Icon(imageVector = icon , contentDescription = "")
            }
        }
        content.invoke()
    }
}

@Preview
@Composable
fun CuisineScreen_() {
    CheicksaTheme {
        //CuisineScreen(navController = rememberNavController())
        Filters(filter= "order")
    }
}

