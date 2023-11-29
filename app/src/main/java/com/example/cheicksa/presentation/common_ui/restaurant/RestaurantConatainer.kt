package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cheicksa.R
import com.example.cheicksa.navigation.RestaurantScreens

/**
 * [RestaurantContainer] is a composable function that represents a restaurant container card in a UI. It displays information
 * about a restaurant, including its image, name, category, minimum order, and delivery fee. The card is clickable, and clicking
 * on it navigates to the restaurant's order screen.
 *
 * @param modifier Modifier for the overall size of the restaurant container card. Default is 235.dp x 145.dp.
 * @param painter The [Painter] used to draw the image representing the restaurant.
 * @param name The name or name of the restaurant.
 * @param category The category or type of cuisine the restaurant belongs to.
 * @param mimOrder The minimum order amount for the restaurant.
 * @param deliveryFee The delivery fee for orders from the restaurant.
 * @param textModifier Modifier for the Text composables within the restaurant container.
 * @param navController The [NavController] used for navigation when the card is clicked.
 * @param id The id passed to the order screen when navigating.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantContainer(
    modifier: Modifier = Modifier
        .width(235.dp)
        .height(145.dp),
    painter: Painter,
    name: String,
    category: String,
    mimOrder: Double,
    deliveryFee: Double,
    textModifier: Modifier = Modifier,
    navController: NavController,
    id: Long,
    emoji: String = ""
) {
    Column(
        modifier = Modifier,
    ) {
        Card(
            onClick = { navController.navigate(RestaurantScreens.Order.route + "/" + id) },
            modifier = modifier,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = textModifier,
            textAlign = TextAlign.Start
        )
        Text(
            text = emoji + stringResource(id = R.string.space) + category,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = textModifier,
            textAlign = TextAlign.Start
        )
        Row (
            modifier = textModifier
        ){
            Text(
                text = mimOrder.toString() + stringResource(R.string.space)
                    + stringResource(id = R.string.devise) + stringResource(R.string.space)
                    + stringResource(R.string.min_order)
                ,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier,
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(R.string.delivery_fee,deliveryFee) +
                        stringResource(R.string.devise) ,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                modifier = Modifier,
                textAlign = TextAlign.Start
            )
        }
    }
}