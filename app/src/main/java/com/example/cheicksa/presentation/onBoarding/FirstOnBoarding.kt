package com.example.cheicksa.presentation.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cheicksa.R
import com.example.cheicksa.presentation.authentification.BlackButton
import com.example.cheicksa.ui.theme.CheicksaTheme

@Composable
fun FirstOnBoarding() {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            painter = painterResource(id = R.drawable.bro),
            contentDescription = "",
            modifier = Modifier
                .height(240.dp)
                .width(255.dp)
        )
        Spacer(modifier = Modifier.height(45.dp))
        Text(
            text = "Bienvenue sur CheickSa",
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
        )
        Text(
            text = "Nous sommes ravis de vous accueillir " +
                    "dans le monde délicieux de CheickSa ! " +
                    "Notre mission est de simplifier votre " +
                    "expérience culinaire en vous offrant un " +
                    "accès facile à une multitude de plats " +
                    "délicieux, où que vous soyez.",
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(35.dp)
        )
        Spacer(modifier = Modifier.height(85.dp))
        BlackButton(
            text = "continuez",
            onClick = {}
        )
    }
}

@Preview
@Composable
fun FirstOnBoarding_() {
    CheicksaTheme {
        FirstOnBoarding()
    }
}