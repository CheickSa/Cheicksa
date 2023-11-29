package com.example.cheicksa.presentation.authentification

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.navigation.AuthScreens
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.viewmodels.FireBaseViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(navController: NavController) {

    val fireBaseViewModel = hiltViewModel<FireBaseViewModel>()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    val signIn by remember { fireBaseViewModel.signedIn }


    Box {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.signin),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            AuthTextField(
                leadingIcon = painterResource(id = R.drawable.email_icon),
                value = email.ifEmpty { stringResource(id = R.string.email) },
                isError = isEmailError,
                onNext = { /*TODO*/ },
                onDone = { /*TODO*/ },
                imeAction = ImeAction.Next,
                isPasswordField = false,
                onValueChange = {
                    email = it
                    isEmailError = false
                },
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(15.dp))
            AuthTextField(
                leadingIcon = painterResource(id = R.drawable.mot_de_passe),
                value = password.ifEmpty { stringResource(id = R.string.password) },
                isError = isPasswordError,
                onNext = { /*TODO*/ },
                onDone = { /*TODO*/ },
                imeAction = ImeAction.Done,
                isPasswordField = true,
                onValueChange = {
                    password = it
                    isPasswordError = false
                },
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            BlackButton(
                onClick = {
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
                    if (email.isNotEmpty() && emailPattern.matches(email) && password.isNotEmpty()) {
                        fireBaseViewModel.login(email, password)
                        if (signIn) {
                            navController.navigate(StoreScreens.HomeSreen.route) {
                                popUpTo(AuthScreens.Login.route) {
                                    inclusive = true
                                }
                            }
                        }else{

                        }
                    }
                },
                text = stringResource(R.string.signin)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Section()
            Spacer(modifier = Modifier.height(30.dp))
            Auth2(
                navigate = {
                    if (signIn) {
                        navController.navigate(StoreScreens.HomeSreen.route) {
                            popUpTo(AuthScreens.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            GoRegister(
                normalText = "Vous n’avez pas de compte? ",
                boldText = "S’inscrire",
                onClick = {
                    navController.navigate(AuthScreens.Register.route)
                }
            )
        }
        if (fireBaseViewModel.inProgress.value) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun LogIn_() {
    CheicksaTheme {
        LogIn(rememberNavController())
    }
}