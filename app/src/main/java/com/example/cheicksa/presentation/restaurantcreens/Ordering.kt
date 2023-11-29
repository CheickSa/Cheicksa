package com.example.cheicksa.presentation.restaurantcreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.model.Bill
import com.example.cheicksa.model.Discount
import com.example.cheicksa.model.OrderScreenCardData
import com.example.cheicksa.presentation.authentification.BlackButton
import com.example.cheicksa.presentation.lesRestaurants
import com.example.cheicksa.presentation.viewmodels.OrderingViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme

@Composable
fun Ordering(
    mealInfo: String?,
    navController: NavController
) {
    var amount by rememberSaveable {
        mutableStateOf(1)
    }
    Column(
        modifier = Modifier
            .padding(30.dp)
    ) {

        val (mealGroupId,restaurantId,mealId) = mealInfo?.split("_")!!
        val deliveryFee = lesRestaurants.find { it.id == restaurantId.toLong() }?.deliveryFee
        val restaurants = lesRestaurants.find { it.id == restaurantId.toLong() }?.mealsList
        val mealGroup = restaurants?.get(mealGroupId.toInt())
        val meal = mealGroup?.orderScreenDatas?.cards?.get(mealId.toInt())

        val bill = Bill(
            initialPrice = meal?.price!!,
            price = meal.price,
            amount = 1,
            deliveryFee = deliveryFee!!,
            tax = 10.0,
            discount = 0.0
        )

        Card(
            shape = RoundedCornerShape(10.dp)
        ) {
            meal.painter.let { painterResource(id = it) }.let {
                Image(
                    painter = it,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(195.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        OrderingInfo(meal,)
        Spacer(modifier = Modifier.height(10.dp))
        OrderingCard(discount,billToUpdate=bill)

    }
}
val discount = Discount(
    discount = "welcome2023",
    amount = 20
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderingCard(
    discount: Discount,
    billToUpdate: Bill,
) {
    val orderingViewModel: OrderingViewModel = viewModel()


    var textValue by rememberSaveable {
        mutableStateOf("")
    }
    val bill by orderingViewModel.bill.collectAsState()

    var applyDiscount by rememberSaveable {
        mutableStateOf(false)
    }

    Column (
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        orderingViewModel.updateBill(billToUpdate)
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                textValue = it
                if (it==discount.discount) {
                    orderingViewModel.applyDiscount(discount = discount.amount)
                    applyDiscount=true
                }else{
                    applyDiscount=false
                    orderingViewModel.applyDiscount(discount = 0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Entrez votre coupon",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )
            },
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                if (discount.amount != 0) {
                    Card(
                        onClick = {
                            if (textValue != "") {
                                textValue = ""
                                orderingViewModel.applyDiscount(discount = 0)
                                applyDiscount = false
                            } else {
                                textValue = discount.discount
                                orderingViewModel.applyDiscount(discount = discount.amount)
                                applyDiscount = true
                            }
                        },
                        modifier = Modifier
                            .height(20.dp)
                            .padding(end = 5.dp),
                        colors = CardDefaults.cardColors(Color(255, 157, 1)),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = discount.amount.toString(),
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        )
        OutlinedCard (
            colors = CardDefaults.cardColors(Color.Unspecified),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Cheese Burger x${bill.amount}",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )
                    Text(
                        text ="${bill.price}",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                }
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Frais de livraison",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )
                    Text(
                        text = "${bill.deliveryFee} TL",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )

                }
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Tax",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )
                    Text(
                        text = "${bill.tax} TL",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Total",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                    Text(
                        text = "${bill.total} TL",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        textDecoration = if (applyDiscount) TextDecoration.LineThrough else TextDecoration.None
                    )
                    if (applyDiscount) {
                        Text(
                            text = "${bill.discount} TL",
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            color = Color.Green
                        )
                    }

                }

            }
        }
        Spacer(modifier = Modifier.height(120.dp))
        BlackButton(
            text = "Confirmer",
            onClick = {},
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
        )

    }
}

@Composable
fun OrderingInfo(
    data: OrderScreenCardData,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .padding(start=10.dp, end=10.dp, top = 10.dp)
        ) {
            Text(
                text = data.title,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.description,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.width(200.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                lineHeight = 1.2.em
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = data.price.toString(),
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
        }
        Counter()
    }
}

@Composable
fun Counter(
    orderingViewModel: OrderingViewModel = viewModel()
) {
    val bill by orderingViewModel.bill.collectAsState()
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.minus_circle_outline),
            contentDescription ="",
            modifier = Modifier
                .clickable { if (bill.amount in 2..9) orderingViewModel.changePrice(increase = false) }

        )
        Text(
            text = bill.amount.toString(),
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
        )

        Icon(
            painter = painterResource(id = R.drawable.plus_circle_outline),
            contentDescription = "",
            modifier = Modifier
                .clickable { if (bill.amount in 1..8) orderingViewModel.changePrice(increase = true)}
        )
    }
}


@Preview
@Composable
fun Ordering_() {
    CheicksaTheme {
        Ordering(mealInfo = "", navController = rememberNavController())
        //Counter()
        //OrderingCard()
    }
}