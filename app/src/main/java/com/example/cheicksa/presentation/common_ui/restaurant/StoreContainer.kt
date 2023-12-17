package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cheicksa.R
import com.example.cheicksa.ui.theme.CheicksaTheme

/**
 * [StoreContainer] is a composable function that represents a store container card in a UI. It is typically used to display
 * information about a specific store, such as its image, name, and other details.
 *
 * @param width The width of the store container card. Default is 165.dp.
 * @param height The height of the store container card. Default is 126.dp.
 * @param painter The [Painter] used to draw the image representing the store. Default is a placeholder image.
 * @param imageModifier Modifier for the Image composable displaying the store image. Default includes width, height, and offset.
 * @param title The name or name of the store. Default is a placeholder string.
 * @param onClick Lambda function invoked when the store container is clicked. Default is an empty function.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreContainer(
    width: Dp = 160.dp,
    height: Dp = 126.dp,
    painter: Painter = painterResource(id = R.drawable.sup_market),
    imageModifier: Modifier,
    title: String,
    onClick: ()-> Unit = {},
    applyComingSoon: Boolean = false
) {
    Card (
        onClick = onClick,
        modifier = Modifier
            .width(width)
            .height(height),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
    ){
        Box (
            modifier = Modifier.fillMaxSize()
        ){
            Image(
                painter = painter,
                contentDescription = "",
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomEnd,
            )
            Row {
                Text(
                    text = title,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                )
                if (applyComingSoon){
                    Text(
                        text = stringResource(id = R.string.comingSoon),
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun StoreContainer_() {
    CheicksaTheme {
        StoreContainer(title = stringResource(id = R.string.superMarket), imageModifier = Modifier)
    }
}