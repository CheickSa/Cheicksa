package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.ui.theme.montSarrat

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
    imageUrl: String? = null,
    title: String = "",
    onClick: () -> Unit = {},
    loading: Boolean = true
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
            val painter = // Add a placeholder drawable
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                        .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            //placeholder(R.drawable.placeholder) // Add a placeholder drawable
                        }).build()
                )

            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect(
                        enabled = painter.state is AsyncImagePainter.State.Loading || loading,
                    ),
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
            modifier = Modifier
                .shimmerEffect(
                    enabled =  loading,
                    secondModifier = Modifier
                        .width(70.dp)
                        .clip(RoundedCornerShape(10.dp))
                ),
            textAlign = TextAlign.Center,
            fontFamily = montSarrat

        )
    }
}