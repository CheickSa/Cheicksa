package com.example.cheicksa.presentation.restaurantcreens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantContainer
import com.example.cheicksa.presentation.lesRestaurants
import com.example.cheicksa.ui.theme.CheicksaTheme
import okhttp3.MediaType.Companion.toMediaType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuisineScreen(
    cuisineCategory: String? = null,
    navController: NavController
) {
    Scaffold (
        topBar = {
            TopAppBar(title = {
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp),

                ){
                    item { Filters(
                        filter = "Prix"
                    ) }
                    item { Filters(
                        filter = "Min. order"
                    ) }
                    item { Filters(
                        filter = "Super Restaurant"
                    ) }
                    item { Filters(
                        filter = "Type de livraison"
                    ) }

                }
            },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        }
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(bottom = 20.dp, top = 20.dp),
            contentPadding = it
        ){
            val restaurants = lesRestaurants.filter { it.category == cuisineCategory }
            items(restaurants.size){
                val restaurant = restaurants[it]
                RestaurantContainer(
                    modifier = Modifier
                        .height(175.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    textModifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    image = restaurant.imageUrl,
                    category = restaurant.category,
                    name = restaurant.name,
                    deliveryFee = restaurant.deliveryFee,
                    mimOrder = restaurant.minOrder,
                    deliveryTime = restaurant.deliveryTime,
                    isVerified = restaurant.isVerified,
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filters(
    content: @Composable ()->Unit = {},
    onClick: ()->Unit = {},
    filter: String
) {
    var openContent by rememberSaveable {
        mutableStateOf(false)
    }
    val icon = if (openContent) Icons.Default.KeyboardArrowUp
    else Icons.Default.KeyboardArrowDown
    Column {
        OutlinedCard(
            modifier = Modifier
                .height(35.dp),
            onClick = {
                openContent = !openContent
                onClick
            },
            colors = CardDefaults.cardColors(Color.Unspecified),

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
        if (openContent){
            content.invoke()
        }

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

