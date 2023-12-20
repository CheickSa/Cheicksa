package com.example.cheicksa.presentation.restaurantcreens

import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.presentation.common_ui.restaurant.OrderContainer
import com.example.cheicksa.presentation.permission.Permission
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.presentation.viewmodels.NotificationViewModel
import com.example.cheicksa.presentation.viewmodels.RoomViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Cart(
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity),
    roomViewModel: RoomViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    navController: NavController,
    notification : NotificationViewModel = viewModel(LocalContext.current as ComponentActivity),
) {

    val restaurants by menuViewModel.restaurants.collectAsState()
    val orders = roomViewModel.orders().asFlow().collectAsState(initial = listOf()).value

    val context = LocalContext.current
    Log.d("Cart", "Cart: $orders")
    Permission()
    orders?.forEach {
        val restaurant = restaurants.find { restaurant -> restaurant.id == it.restaurantId }
        val meal = restaurant?.mealsList?.find { meal -> meal.mealListCollectionIds == it.mealListId }
        val card = meal?.cards?.find { card -> card.id == it.mealId }

        Log.d("Cart", "Cart: $card")
        Log.d("Cart", "Cart: $it")
        Log.d("Cart", "Cart: $restaurant")
        Log.d("Cart", "Cart: $meal")
        if (card != null) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            ) {

                OrderContainer(
                    foodName = card.title,
                    ingredient = card.description,
                    price = card.price,
                ) {
                    notification.sendNotification(
                        title = "Cheicksa",
                        message = "Your order is on the way",
                        context = context
                    )
                }
                Text(text = "Extras")
                it.extras.forEach { extra ->
                    Text(text = extra.name)
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun _Cart() {
    CheicksaTheme {
        Cart(navController = rememberNavController())
    }
}