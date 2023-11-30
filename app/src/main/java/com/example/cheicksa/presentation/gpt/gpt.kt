package com.example.cheicksa.presentation.gpt

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.presentation.viewmodels.GptViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme


@Composable
fun ChatScreen(navController: NavController) {
    val chatViewModel = hiltViewModel<GptViewModel>()
    val response by chatViewModel.response.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        LazyColumn {
            items(1) {
                Box {
                    Text(text = response.toString())
                }
            }
        }
        MessageField(
            navController = navController,
            onAddClick = {},
            onSendClick = {
                chatViewModel.setRequest()
            },
            onMessageChange = {},
            onMicroClick = {}
        )
    }
}




@Composable
fun BoxScope.MessageField(
    navController: NavController,
    placeHolder: String = "Type a message" + " ".repeat(10), // avoid the suppression of the message when the user type the same thing as the placeholder
    onAddClick: ()-> Unit = {},
    onSendClick: (String)-> Unit = {},
    onMessageChange: (String)-> Unit = {},
    onMicroClick: ()-> Unit = {},

) {
    var text by remember { mutableStateOf("") }
    // android:windowSoftInputMode="adjustResize" in <Activity> in AndroidManifest.xml is required for this to work
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 5.dp,
                end = 16.dp
            )
            .align(Alignment.BottomCenter)
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .weight(1f)
                .clickable(onClick = onAddClick)
        )
        BasicTextField(
            value = text.replaceFirst(placeHolder, "").ifEmpty { placeHolder },
            onValueChange = {
                text = it
                onMessageChange(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {

                }
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = if (text.isEmpty()) Color.Gray else Color.Black
            ),
            modifier = Modifier
                .weight(7f)
                .padding(top = 8.dp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(end = 10.dp, start = 10.dp, top = 5.dp, bottom = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Box(
                        modifier = Modifier
                            .width(245.dp)

                    ) {
                        innerTextField()
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.micro),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable(onClick = onMicroClick)
                    )


                }
            }
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            Icons.Default.Send,
            contentDescription = null,
            modifier = Modifier
                .rotate(-45f)
                .weight(1f)
                .clickable(
                    enabled = text.isNotEmpty(),
                ) {
                    onSendClick(text)
                    text = ""
                }
        )
    }



}

@Preview(showBackground = true)
@Composable
fun KeyboardAwareTextFieldPreview() {
    CheicksaTheme {
        ChatScreen(rememberNavController())
    }
}
