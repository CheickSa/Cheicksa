package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.maxkeppeker.sheets.core.views.Grid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealContainer(modifier: Modifier = Modifier) {
    val list = listOf(
        "aasd","sdsds","dccdacc","aas","sds","sx","jbjjbj","jnnjjn")
    Grid(modifier = Modifier , items = list , columns = 3 , rowSpacing = 10.dp , columnSpacing = 10.dp) {
        Card(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(100.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Text(text = it)
        }
    }
}

@Preview
@Composable
private fun _MealContainer() {
    CheicksaTheme {
        MealContainer()
    }
}