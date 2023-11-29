package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.cheicksa.R

/**
 * [OrderContainer] is a composable function that represents a container card for displaying information about a food item in an order.
 * It includes the food name, ingredients, price, and an image. The card is clickable, and clicking on it invokes the provided [onClick] lambda.
 *
 * @param foodName The name of the food item.
 * @param ingredient The ingredients or description of the food item.
 * @param price The price of the food item.
 * @param painter The resource ID of the image representing the food item.
 * @param onClick Lambda function invoked when the order container is clicked.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderContainer(
    foodName: String,
    ingredient: String,
    price: Double,
    painter: Int,
    onClick: () -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
    ){
        // Card composable for the order container
        Card(
            onClick = onClick ,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            // Row composable for arranging content horizontally
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    modifier = Modifier
                        .padding(start=10.dp, end=10.dp, top = 10.dp)
                ) {
                    Text(
                        text = foodName,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )
                    Text(
                        text = ingredient,
                        fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(200.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        lineHeight = 1.2.em
                    )
                    Text(
                        text = price.toString() +" "+ stringResource(id = R.string.devise) +" ",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )
                }
                Image(
                    painter = painterResource(id = painter),
                    contentDescription = "",
                    modifier = Modifier
                        .width(95.dp)
                        .height(95.dp)
                )
            }
        }
        IconButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.vector_plus),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = (-3).dp, x = (-1).dp),
                tint = Color.Unspecified
            )
        }
    }
}