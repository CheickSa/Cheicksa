package com.example.cheicksa.presentation.authentification

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.IntentSender
import android.provider.Settings
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.navigation.AuthScreens
import com.example.cheicksa.navigation.StoreScreens
import com.example.cheicksa.presentation.viewmodels.FireBaseViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavController) {
    val fireBaseViewModel = hiltViewModel<FireBaseViewModel>()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    var checked by remember { mutableStateOf(false) }
    var showCheckBoxError by remember { mutableStateOf(false) }

    val errorMessage by remember { fireBaseViewModel.popupNotification }
    val signedIn by remember { fireBaseViewModel.signedIn }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.register),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            AuthTextField(
                leadingIcon = painterResource(id = R.drawable.profile),
                value = name.ifEmpty { stringResource(R.string.name_surname) },
                isError = isNameError,
                onNext = { /*TODO*/ },
                onDone = { /*TODO*/ },
                imeAction = ImeAction.Next,
                isPasswordField = false,
                onValueChange = { name = it; isNameError = false }
            )
            Spacer(modifier = Modifier.height(15.dp))
            AuthTextField(
                leadingIcon = painterResource(id = R.drawable.email_icon),
                value = email.ifEmpty { stringResource(R.string.email) },
                isError = isEmailError,
                onNext = { /*TODO*/ },
                onDone = { /*TODO*/ },
                imeAction = ImeAction.Next,
                isPasswordField = false,
                keyboardType = KeyboardType.Email,
                onValueChange = { email = it; isEmailError = false }
            )
            Spacer(modifier = Modifier.height(15.dp))
            AuthTextField(
                leadingIcon = painterResource(id = R.drawable.telephone_icon),
                value = phone.ifEmpty { stringResource(R.string.phone_number) },
                isError = isPhoneError,
                onNext = { /*TODO*/ },
                onDone = { /*TODO*/ },
                imeAction = ImeAction.Next,
                isPasswordField = false,
                keyboardType = KeyboardType.Phone,
                onValueChange = { phone = it; isPhoneError = false }
            )
            Spacer(modifier = Modifier.height(15.dp))
            AuthTextField(
                leadingIcon = painterResource(id = R.drawable.mot_de_passe),
                value = password.ifEmpty { stringResource(R.string.password) },
                isError = isPasswordError,
                onNext = { /*TODO*/ },
                onDone = { /*TODO*/ },
                imeAction = ImeAction.Next,
                isPasswordField = true,
                onValueChange = { password = it; isPasswordError = false }
            )
            Spacer(modifier = Modifier.height(30.dp))
            BlackButton(
                onClick = {
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
                    when {
                        name.isEmpty() -> {
                            isNameError = true
                            // fireBaseViewModel.popupNotification.value = "Veuillez remplir tous les champs"
                        }

                        email.isEmpty() || !email.matches(emailPattern) -> isEmailError = true
                        phone.isEmpty() -> isPhoneError = true
                        password.isEmpty() -> isPasswordError = true
                        !checked -> showCheckBoxError = true
                        checked -> showCheckBoxError = false
                        !isNameError && !isEmailError && !isPhoneError && !isPasswordError && !showCheckBoxError -> {
                            fireBaseViewModel.onSignUp(email, password, phone)
                            //TODO : impliment email verification and phone verification
                            if (signedIn) {
                                navController.navigate(StoreScreens.HomeSreen.route) {
                                    popUpTo(AuthScreens.Register.route) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    navController.context,
                                    errorMessage.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },
                text = stringResource(R.string.register)
            )
            Spacer(modifier = Modifier.height(10.dp))
            PolicyText(
                checked = checked,
                onClick = { checked = it },
                showError = showCheckBoxError

            )
            Spacer(modifier = Modifier.height(40.dp))
            Section()
            Spacer(modifier = Modifier.height(30.dp))
            Auth2(
                navigate = {
                    if (signedIn) {
                        navController.navigate(StoreScreens.HomeSreen.route) {
                            popUpTo(AuthScreens.Register.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            GoRegister(
                onClick = { navController.navigate(AuthScreens.Login.route) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlackButton(
    modifier: Modifier = Modifier
        .padding(start = 30.dp, end = 30.dp),
    onClick: ()->Unit = {},
    text: String = "S’inscrire",
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .then(modifier),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    leadingIcon : Painter? = painterResource(id = R.drawable.profile ),
    value: String = "Nom prenom",
    isError: Boolean = false,
    onNext: ()->Unit = {},
    onDone: ()->Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    isPasswordField: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit = { },
    focusRequester: FocusRequester = FocusRequester(),

    ) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by rememberSaveable { mutableStateOf(true) }

    val passwordIcon = if (passwordVisibility)
        painterResource(id = R.drawable.visibility)
    else painterResource(id = R.drawable.visibility_off)

    val focusRequester = remember { focusRequester }

    Card (
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ){
        TextField(
            placeholder = {
                Text(text = value)
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxSize(),
            value = text,
            onValueChange = {
                text =  it
                onValueChange.invoke(it)

            },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        painter = leadingIcon,
                        contentDescription = ""
                    )
                }
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Unspecified
            ),
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = if (isPasswordField) KeyboardType.Password
                else keyboardType
            ),
            keyboardActions = KeyboardActions(
                onNext = { onNext.invoke() },
                onDone = { onDone.invoke() }
            ),
            shape = MaterialTheme.shapes.medium,
            visualTransformation = if (isPasswordField && !passwordVisibility) PasswordVisualTransformation()
            else if (isPasswordField && passwordVisibility) VisualTransformation.None
            else VisualTransformation.None,
            trailingIcon = {
                if (isPasswordField) {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(painter = passwordIcon, contentDescription = "")
                    }
                }
            },
        )
    }
}

@Composable
fun Section() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Divider(modifier = Modifier
            .width(145.dp)
            .padding(end = 5.dp))
        Text(
            text = " ou ",
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            color = MaterialTheme.colorScheme.primary
        )
        Divider(modifier = Modifier
            .width(145.dp)
            .padding(start = 5.dp))

    }
}

@Composable
fun PolicyText(
    onClick: (Boolean) -> Unit = {},
    checked: Boolean = false,
    showError: Boolean = false
) {
    // var checked by remember { mutableStateOf(checked) }

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = checked ,
            onCheckedChange = { onClick.invoke(it) },
            modifier = Modifier
                .height(16.dp)
                .width(16.dp),
            colors = CheckboxDefaults.colors(uncheckedColor = if (showError) Color.Red else MaterialTheme.colorScheme.primary),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "J’accepte les ",
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "termes & conditions",
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = " d'utilisation",
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = MaterialTheme.colorScheme.secondary
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Auth2(
    navigate: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val fireBaseViewModel = hiltViewModel<FireBaseViewModel>()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->

        if (result.resultCode == RESULT_OK) {
            scope.launch {
                fireBaseViewModel.signInWithIntent(
                    intent = result.data ?: return@launch
                )
            }

        } else {
            // Handle errors.
            Log.d(TAG, "Handle errors.")
        }
    }

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ){
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(80.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
            border = BorderStroke(1.67.dp, MaterialTheme.colorScheme.primary),
            onClick = {
                scope.launch {
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            fireBaseViewModel.sendAuthRequest() ?: return@launch
                        ).build()
                    )
                }

            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.google_basic),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }

        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(80.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
            border = BorderStroke(1.67.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.facebook_blue),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }
    }
    navigate.invoke()
}

@Composable
fun GoRegister(
    normalText: String = "Vous avez un compte? ",
    boldText: String = "Se Connecter",
    onClick: () -> Unit = {}
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){

        Text(
            text = normalText,
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = boldText,
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable (onClick = onClick)
        )

    }
}

@Preview
@Composable
fun SignIn_() {
    CheicksaTheme {
        SignIn(rememberNavController())
    }
}