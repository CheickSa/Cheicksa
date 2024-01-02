package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.cheicksa.R
import com.example.cheicksa.ui.theme.montSarrat

/**
 * [RestaurantContainer] is a composable function that represents a restaurant container card in a UI. It displays information
 * about a restaurant, including its image, name, category, minimum order, and delivery fee. The card is clickable, and clicking
 * on it navigates to the restaurant's order screen.
 *
 * @param modifier Modifier for the overall size of the restaurant container card. Default is 235.dp x 145.dp.
 * @param iconModifier Modifier for the icons within the restaurant container.
 * @param name The name or name of the restaurant.
 * @param category The category or type of cuisine the restaurant belongs to.
 * @param mimOrder The minimum order amount for the restaurant.
 * @param deliveryFee The delivery fee for orders from the restaurant.
 * @param textModifier Modifier for the Text composables within the restaurant container.
 * @param onClick The action to perform when the restaurant container is clicked.
 * @param deliveryTime The estimated delivery time for orders from the restaurant.
 * @param emoji The emoji associated with the restaurant's cuisine.
 * @param favorite Whether the restaurant is a favorite or not.
 * @param onFavoriteClick The action to perform when the favorite icon is clicked.
 * @param isVerified Whether the restaurant is verified or not.
 * @param loading Whether the restaurant container is loading or not.
 * @param image The image of the restaurant.
 * @author Mamadou
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantContainer(
    modifier: Modifier = Modifier
        .width(235.dp)
        .height(145.dp),
    iconModifier: Modifier = Modifier,
    image: String? = null,
    name: String= "",
    category: String = "",
    mimOrder: Double? = null,
    deliveryFee: Double = 0.0,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    deliveryTime: Double=0.0,
    emoji: String = "",
    favorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    isVerified: Boolean= false,
    loading: Boolean = false
) {
    var isFavorite by remember { mutableStateOf(favorite) }

    Column(
        modifier = Modifier,
    ) {
        Box {
            Card(
                onClick = onClick,
                modifier = modifier,
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {
                val painter = // Add a placeholder drawable
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = image)
                            .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                //placeholder(R.drawable.placeholder) // Add a placeholder drawable
                            }).build()
                    )
                Image(
                    painter = painter,
                    //error = painterResource(id = R.drawable.burger),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect(
                            enabled = loading || painter.state is AsyncImagePainter.State.Loading,
                            secondModifier = Modifier
                        ),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
            }
            if (!loading) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(5.dp,)
                        .then(iconModifier)
                    ,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(
                        text = deliveryTime.toString()
                            .replace(".00", "")
                            .replace(".0", "") +
                                stringResource(R.string.space) + stringResource(R.string.min_order),
                        fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .padding(3.dp),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = montSarrat
                    )
                }

                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(5.dp)
                        .then(iconModifier),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            //.align(Alignment.TopEnd)
                            .padding(1.dp)
                            .size(18.dp)
                            .clickable {
                                isFavorite = !isFavorite
                                onFavoriteClick.invoke()
                            }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = name,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = textModifier
                    .shimmerEffect(
                        enabled = loading,
                        secondModifier = Modifier
                            .width(150.dp)
                            .padding(3.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ),
                textAlign = TextAlign.Start,
                fontFamily = montSarrat

            )
            Spacer(modifier = Modifier.width(5.dp))
            if (isVerified){
                Icon(
                    painter = painterResource(id = R.drawable.badge),
                    contentDescription = "",
                    tint = Color.Blue.copy(alpha = 0.5f),
                )
            }
        }
        Text(
            text = "$category - " + if (mimOrder != null)  mimOrder.toString() + stringResource(R.string.space)
                    + stringResource(id = R.string.devise) + stringResource(R.string.space)
                    + stringResource(R.string.min_order)
            else "",
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = textModifier
                .shimmerEffect(
                    enabled = loading,
                    secondModifier = Modifier
                        .width(90.dp)
                        .padding(3.dp)
                        .clip(RoundedCornerShape(10.dp))
                ),
            textAlign = TextAlign.Start,
            fontFamily = montSarrat
        )
        Row (
            modifier = textModifier
                .shimmerEffect(
                    enabled = loading,
                    secondModifier = Modifier
                        .width(200.dp)
                        .padding(3.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
        ){
//            Text(
//                text = if (mimOrder != null)  mimOrder.toString() + stringResource(R.string.space)
//                    + stringResource(id = R.string.devise) + stringResource(R.string.space)
//                    + stringResource(R.string.min_order)
//                else ""
//                ,
//                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
//                fontSize = MaterialTheme.typography.titleSmall.fontSize,
//                color = MaterialTheme.colorScheme.primary,
//                modifier = Modifier,
//                textAlign = TextAlign.Start,
//                fontFamily = montSarrat
//            )
            Text(
                text = if (mimOrder != null) stringResource(R.string.delivery_fee,deliveryFee) +
                        stringResource(R.string.devise) else "",
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                modifier = Modifier,
                textAlign = TextAlign.Start,
                fontFamily = montSarrat
            )
        }
    }
}