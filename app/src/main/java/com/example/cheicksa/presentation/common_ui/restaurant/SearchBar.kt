package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cheicksa.R
import com.example.cheicksa.ui.theme.CheicksaTheme
import kotlinx.coroutines.delay

/**
 * [SearchBar] is the search bar composable function
 * @param height the optional height parameter is set to 44dp by default
 * @param color the optional parameter for the color of the bar is set to secondary of the color sheme
 * @param onClick what do you except the search bar to do when user click on it
 * @param text is an animate text that will appear inside the container of the search bar
 * @param description set to empty text by default fill it for text testing purpose
 */

@Composable
fun SearchBar(
    height: Dp = 44.dp,
    color: Color = MaterialTheme.colorScheme.secondary,
    onClick: ()-> Unit,
    text: String,
    description: String? = null,
) {
    var textToAnimate by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        text.toCharArray().forEach {
            textToAnimate += it
            delay(100)
        }
    }
    Row (
        modifier= Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .height(height)
                .clickable(onClick = onClick),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(color)
        ){
            Row (
                modifier= Modifier
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = onClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = description,
                        tint = Color.Unspecified
                    )
                }
                Text(
                    text = textToAnimate,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}

@Preview
@Composable
fun SearchBar_() {
    CheicksaTheme {
        SearchBar(onClick = { /*TODO*/ }, text = "Chercher ce que vous voulez")
    }
}