package com.example.cheicksa.presentation.restaurantcreens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.OrderInfo
import com.example.cheicksa.presentation.common_ui.restaurant.OrderContainer
import com.example.cheicksa.presentation.common_ui.restaurant.shimmerEffect
import com.example.cheicksa.presentation.permission.Permission
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.presentation.viewmodels.NotificationViewModel
import com.example.cheicksa.presentation.viewmodels.RoomViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.example.cheicksa.ui.theme.montSarrat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    val database = Firebase.database
    val myRef = database.reference.child("orders")


    val scope = rememberCoroutineScope()


    val context = LocalContext.current
    Log.d("Cart", "Cart: $orders")
    Permission()
    Scaffold(
        topBar={
            val restaurant = restaurants.find { restaurant ->  orders.all { restaurant.id == it.restaurantId } }
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Panier",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = montSarrat,
                            color = Color.Black
                        )
                        Text(
                            text = restaurant?.name ?: "",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = montSarrat,
                            color = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },

            )
        },
        bottomBar = {
            BottomAppBar (
                contentPadding = PaddingValues(5.dp)
            ){
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(text = "Confirmer")
                }
            }
        }
    ) {
        LazyColumn(
            contentPadding = it
        ) {
            items(orders.size) { index ->
                ReceiptContainer(
                    orderReceipt = orders[index],
                    onUpdateMeal = {
                        scope.launch {
                            roomViewModel.upsertOrder(it)
                        }
                    },
                    onDelete = {
                        scope.launch {
                            roomViewModel.deleteOrderById(orders[index].id)
                        }
                    },
                    onClick = {
                        // TODO: Impliment the order processing
                        myRef.child(orders[index].restaurantId)
                            .setValue(orders[index])
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    scope.launch {
                                        roomViewModel.deleteOrderById(orders[index].id)
                                    }
                                }else {
                                    Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                )
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptContainer(
    orderReceipt: OrderInfo,
    onUpdateMeal : (OrderInfo)->Unit = {},
    onDelete: ()->Unit = {},
    onClick: ()->Unit = {}

) {
    var quantity by remember { mutableStateOf(orderReceipt.quantity) }
    var showUpdateButtons by remember { mutableStateOf(false) }
    var totalPrice by remember { mutableStateOf(orderReceipt.totalPrice) }
    Card (
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                val image = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
                    .data(orderReceipt.imageUlr)
                    .build())
                Row (
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = image,
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(shape = CircleShape)
                            .shimmerEffect(
                                enabled = image.state is AsyncImagePainter.State.Loading,
                            ),
                        contentScale = ContentScale.Crop,
                    )
                    Column {
                        Text(
                            text = orderReceipt.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = montSarrat,
                            color = Color.Black
                        )
                        Text(
                            text = "$quantity - Total: $totalPrice " + stringResource(
                                id = R.string.devise
                            ),
                            fontSize = 15.sp,
                            fontFamily = montSarrat,
                            color = Color.Black
                        )
                        if (orderReceipt.address != null) {
                            Text(
                                text = "Livraison à ${orderReceipt.address.city} ${orderReceipt.address.line1}",
                                fontSize = 15.sp,
                                fontFamily = montSarrat,
                                color = Color.Black
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    if (showUpdateButtons) {
                        OutlinedButton(
                            onClick = {
                                if (quantity < 10) { // Limit the quantity, adjust as needed
                                    quantity++
                                    totalPrice += orderReceipt.initPrice
                                    onUpdateMeal(orderReceipt.copy(quantity = quantity, totalPrice = totalPrice))
                                }
                            },
                            modifier = Modifier
                                .size(25.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "+",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = montSarrat,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            showUpdateButtons = !showUpdateButtons
                        },
                        modifier = Modifier
                            .size(25.dp),
                        contentPadding = PaddingValues(0.dp),
                        border = if (showUpdateButtons) null else ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text(
                            text = quantity.toString(),
                            fontSize = 16.sp,
                            fontFamily = montSarrat,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (showUpdateButtons) {
                        OutlinedButton(
                            onClick = {
                                if (quantity > 1) {
                                    quantity--
                                    totalPrice -= orderReceipt.initPrice
                                    onUpdateMeal(orderReceipt.copy(quantity = quantity, totalPrice = totalPrice))
                                }else {
                                    onDelete()
                                }
                            },
                            modifier = Modifier
                                .size(25.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "-",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = montSarrat,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
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



