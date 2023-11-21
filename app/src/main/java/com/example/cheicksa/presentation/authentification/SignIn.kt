package com.example.cheicksa.presentation.authentification

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cheicksa.R
import com.example.cheicksa.ui.theme.CheicksaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn() {
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
                text = "S’inscrire",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        AuthTextField(
            leadingIcon = painterResource(id = R.drawable.profile ),
            value = "Nom prenom",
            isError = false,
            onNext = { /*TODO*/ },
            onDone = { /*TODO*/ },
            imeAction = ImeAction.Next,
            isPasswordField = false
        )
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
            leadingIcon = painterResource(id = R.drawable.telephone_icon ),
            value = "Numero de telephone",
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
        Spacer(modifier = Modifier.height(30.dp))
        BlackButton(
            onClick = {},
            text = "S’inscrire"
        )
        Spacer(modifier = Modifier.height(10.dp))
        PolicyText()
        Spacer(modifier = Modifier.height(40.dp))
        Section()
        Spacer(modifier = Modifier.height(30.dp))
        Auth2()
        Spacer(modifier = Modifier.height(25.dp))
        GoRegister()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlackButton(
    onClick: ()->Unit = {},
    text: String = "S’inscrire",
    modifier: Modifier = Modifier
        .padding(start = 30.dp, end = 30.dp)

) {
    Card(
        onClick = onClick,
        modifier = modifier
            .height(55.dp)
            .fillMaxWidth(),
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
    leadingIcon : Painter = painterResource(id = R.drawable.profile ),
    value: String = "Nom prenom",
    isError: Boolean = false,
    onNext: ()->Unit = {},
    onDone: ()->Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    isPasswordField: Boolean = false

) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by rememberSaveable { mutableStateOf(true) }

    val passwordIcon = if (passwordVisibility)
        painterResource(id = R.drawable.visibility)
    else painterResource(id = R.drawable.visibility_off)

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
                .fillMaxSize(),
            value = text,
            onValueChange = { text = it },
            leadingIcon = {
                Icon(
                    painter = leadingIcon,
                    contentDescription = ""
                )
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
                imeAction = imeAction
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
            }
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
fun PolicyText() {
    var checked by remember { mutableStateOf(false) }

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = checked ,
            onCheckedChange = {checked=it},
            modifier = Modifier
                .height(16.dp)
                .width(16.dp)
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

@Composable
fun Auth2() {
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
            border = BorderStroke(1.67.dp, MaterialTheme.colorScheme.primary)
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
}

@Composable
fun GoRegister(
    normalText: String = "Vous avez un compte? ",
    boldText: String = "Se Connecter"
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
            color = MaterialTheme.colorScheme.primary
        )

    }
}

@Preview
@Composable
fun SignIn_() {
    CheicksaTheme {
        SignIn()
    }
}