package com.example.cheicksa.presentation.splashscreen

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.cheicksa.ui.theme.CheicksaTheme

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cheick",
            fontSize = 70.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = "Sa",
            fontSize = 70.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.tertiary
        )
    }

}

@Preview
@Composable
fun SplashScreen_() {
    CheicksaTheme {
        SplashScreen()
    }
}