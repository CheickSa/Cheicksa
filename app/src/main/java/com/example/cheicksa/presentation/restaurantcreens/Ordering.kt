package com.example.cheicksa.presentation.restaurantcreens

import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.Extra
import com.example.cheicksa.model.restaurant.OrderInfo
import com.example.cheicksa.navigation.RestaurantScreens
import com.example.cheicksa.presentation.common_ui.restaurant.ShowOrderDetails
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.presentation.viewmodels.RoomViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ordering(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity),
    roomViewModel: RoomViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val data by menuViewModel.order.collectAsState()
    val restaurant by menuViewModel.restaurant.collectAsState()
    var amount = remember { mutableStateOf(1) }
    var instructions by remember { mutableStateOf("") }
    var extraList by remember { mutableStateOf<List<Extra>>(listOf()) }
    val scope = rememberCoroutineScope()

    val orders = roomViewModel.orders().asFlow().collectAsState(initial = listOf()).value

    val snackBarHostState = remember { SnackbarHostState() }
    val useCase = rememberUseCaseState()

    Scaffold (
        snackbarHost = {
            SnackbarHost(snackBarHostState){
                Snackbar(snackbarData = it,)
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .align(Alignment.CenterVertically),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row (){
                        Icon(
                            painter = painterResource(id = R.drawable.plus_circle_outline),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .width(34.dp)
                                .height(34.dp)
                                .clickable { amount.value++ }
                        )
                        Text(
                            text = amount.value.toString(),
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.minus_circle_outline),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .width(34.dp)
                                .height(34.dp)
                                .clickable {
                                    if (amount.value > 1) {
                                        amount.value--
                                    }
                                }
                        )

                    }
                    val mealListId = restaurant.mealsList.find { it.cards.contains(data) }?.mealListCollectionIds
                    val orderTobePlace = OrderInfo(
                        restaurantId = restaurant.id,
                        mealId = data.id,
                        extras = extraList,
                        quantity = amount.value,
                        totalPrice = (data.price * amount.value),
                        mealListId = mealListId ?: "",
                        title = data.title,
                        specialRequest = instructions,
                        time = LocalDateTime.now().toString(),
                        imageUlr = data.imageUlr,
                        initPrice = data.price,
                        userId = Firebase.auth.currentUser?.uid ?: ""
                    )
                    Card(
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(top = 10.dp, bottom = 10.dp, end = 10.dp, start = 10.dp)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            scope.launch {
                                if (!orders.isEmpty() && orders.any{it.restaurantId != orderTobePlace.restaurantId}){
                                    useCase.show()
                                    return@launch
                                }
                                val existingOrder = orders.find { it.mealId == orderTobePlace.mealId }
                                if (existingOrder != null) {
                                    roomViewModel.upsertOrder(existingOrder.copy(
                                        quantity = orderTobePlace.quantity + existingOrder.quantity,
                                        totalPrice = orderTobePlace.totalPrice + existingOrder.totalPrice,
                                        extras = (orderTobePlace.extras + existingOrder.extras).toSet().toList()
                                    ))
                                } else {
                                    roomViewModel.insertOrder(orderTobePlace)
                                }
                                //navController.navigate(RestaurantScreens.Cart.route)
                                val action = snackBarHostState.showSnackbar(
                                    message = "Commande ajoutée au panier",
                                    actionLabel = "Panier",
                                    duration = SnackbarDuration.Short,
                                )
                                if (action == SnackbarResult.ActionPerformed) {
                                    navController.navigate(RestaurantScreens.Cart.route)
                                }
                            }

                            //Log.d("Ordering", "Ordering: $orders")
                            Log.d("Ordering", "Ordering: ${orderTobePlace}")
                        }

                    ) {
                        Text(
                            text = stringResource(id = R.string.add_to_cart),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center

                        )
                        ShowOrderDetails(
                            state = useCase,
                            title = "Vous avez une commande en cours",
                            negativeText = "Annuler",
                            positiveText = "Vider et Ajouter",
                            onPositiveClick = {
                                scope.launch {
                                    roomViewModel.deleteAllOrders()
                                    val existingOrder = orders.find { it.mealId == orderTobePlace.mealId }
                                    if (existingOrder != null) {
                                        roomViewModel.upsertOrder(existingOrder.copy(
                                            quantity = orderTobePlace.quantity + existingOrder.quantity,
                                            totalPrice = orderTobePlace.totalPrice + existingOrder.totalPrice,
                                            extras = (orderTobePlace.extras + existingOrder.extras).toSet().toList()
                                        ))
                                    } else {
                                        roomViewModel.insertOrder(orderTobePlace)
                                    }
                                    //navController.navigate(RestaurantScreens.Cart.route)
                                    val action = snackBarHostState.showSnackbar(
                                        message = "Commande ajoutée au panier",
                                        actionLabel = "Panier",
                                        duration = SnackbarDuration.Short,
                                    )
                                    if (action == SnackbarResult.ActionPerformed) {
                                        navController.navigate(RestaurantScreens.Cart.route)
                                    }

                                }
                            }
                        )

                    }
                }
            }
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { navController.navigate(RestaurantScreens.Cart.route) }
                        //TODO: navigate to cart
                    ) {
                        val image = data.imageUlr
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(image)
                                .build(),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize(),
                            error = painterResource(id = R.drawable.cheeseburger)
                        )
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
                //Divider(modifier = Modifier.padding(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.title,
                        modifier = Modifier,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = data.price.toString() + " " + stringResource(id = R.string.devise) + " ",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )
                }
                Text(
                    text = data.description,
                    modifier = Modifier.padding(start = 10.dp , end = 40.dp),
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Divider(modifier = Modifier.padding(10.dp))
            }

            items(data.extra.size) {
                val extra = data.extra[it]
                var checked by remember { mutableStateOf(false) }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        Checkbox(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                if (checked) {
                                    extraList += listOf(extra)
                                } else {
                                    extraList = extraList.filter { it !in listOf(extra) }
                                }
                            },
                            modifier = Modifier
                                .height(16.dp)
                                .width(16.dp),
                        )
                        Text(
                            text = extra.name,
                            modifier = Modifier,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Text(
                        text = extra.price + " " + stringResource(id = R.string.devise) + " ",
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )

                }
                Spacer(modifier = Modifier.height(5.dp))

            }
            item {
                Divider(modifier = Modifier.padding(10.dp))
                Column(
                    modifier = Modifier.padding(start = 10.dp , end = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.special_instructions),
                        modifier = Modifier,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(id = R.string.special_instructions_description),
                        modifier = Modifier.padding( end = 10.dp),
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 1.2.em,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = instructions,
                        onValueChange = { instructions = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.special_instructions_label),
                                modifier = Modifier,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(10.dp),

                        )
                }
            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun _Ordering() {
    CheicksaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Ordering(rememberNavController())
        }
    }
}