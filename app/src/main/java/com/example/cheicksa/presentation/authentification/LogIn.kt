package com.example.cheicksa.presentation.authentification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cheicksa.R
import com.example.cheicksa.ui.theme.CheicksaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "S’indentifier",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        AuthTextField(
            leadingIcon = painterResource(id = R.drawable.email_icon ),
            value = "Email",
            isError = false,
            onNext = { /*TODO*/ },
            onDone = { /*TODO*/ },
            imeAction = ImeAction.Next,
            isPasswordField = false
        )
        Spacer(modifier = Modifier.height(15.dp))
        AuthTextField(
            leadingIcon = painterResource(id = R.drawable.mot_de_passe ),
            value = "Mot de passe",
            isError = false,
            onNext = { /*TODO*/ },
            onDone = { /*TODO*/ },
            imeAction = ImeAction.Next,
            isPasswordField = true
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(end = 30.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Mot de passe oublie?",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        BlackButton(
            onClick = {},
            text = "S’indentifier"
        )
        Spacer(modifier = Modifier.height(40.dp))
        Section()
        Spacer(modifier = Modifier.height(30.dp))
        Auth2()
        Spacer(modifier = Modifier.height(25.dp))
        GoRegister(
            normalText = "Vous n’avez pas de compte? ",
            boldText = "S’inscrire"
        )
    }
}

@Preview
@Composable
fun LogIn_() {
    CheicksaTheme {
        LogIn()
    }
}