package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp


/**
 * [RestaurantInfoContainer] is a composable function that represents an information container for a restaurant. It typically
 * includes an icon, details, and additional information. The container is clickable, and an [onClick] lambda can be provided
 * for interaction.
 *
 * @param painter The [Painter] used to draw the icon representing the restaurant information.
 * @param details The main details or information about the restaurant.
 * @param additionalDetail Additional information or details complementing the main information.
 * @param color The color of the text representing the main details. Default is [MaterialTheme.colorScheme.primary].
 * @param onClick Lambda function invoked when the restaurant information container is clicked.
 */

@Composable
fun RestaurantInfoContainer(
    painter: Painter,
    details: String,
    additionalDetail: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: ()->Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable (onClick = onClick)
    ){
        Icon(
            painter = painter ,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(7.dp))
        Row {
            Text(
                text = details,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                color = color
            )
            Text(
                text = additionalDetail,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            )
        }
    }
}