package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * [CuisineContainer] is a composable function used to display a cuisine container card in a UI. It typically consists of an
 * image representing the cuisine and a name. The card is clickable, and an [onClick] lambda can be provided for interaction.
 *
 * @param painter The [Painter] used to draw the image representing the cuisine.
 * @param title The name or name of the cuisine.
 * @param onClick Lambda function invoked when the cuisine container is clicked.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuisineContainer(
    painter: Painter,
    title: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(90.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .width(90.dp)
                .height(80.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            onClick = onClick
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = title,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier,
            textAlign = TextAlign.Center
        )
    }
}