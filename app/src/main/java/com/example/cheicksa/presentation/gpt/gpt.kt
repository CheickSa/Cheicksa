package com.example.cheicksa.presentation.gpt

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.R
import com.example.cheicksa.model.gpt.response.ResponseObject
import com.example.cheicksa.presentation.common_ui.restaurant.RestaurantContainer
import com.example.cheicksa.presentation.viewmodels.GptViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.example.cheicksa.ui.theme.shape
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json


@Composable
fun ChatScreen(navController: NavController) {
    val chatViewModel = hiltViewModel<GptViewModel>()
    val chat by chatViewModel.chats
    val thinking by chatViewModel.thinking
    val response by chatViewModel.response.collectAsState()
    val toolData by chatViewModel.toolData.collectAsState()
    val restaurants by chatViewModel.restaurantsList.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            items(chat.chunked(2).size) { index ->
                val message = chat.chunked(2)[index]

                Box {
                    Column {
                        ChatContainer(
                            text = message[0],
                            isAssistant = false
                        )

                        if (message.size == 2 && message[1]!="null") {
                            var textToAnimate by remember { mutableStateOf("") }
                            val text = message[1]
                            LaunchedEffect(text) {
                                text.toCharArray().forEach {
                                    textToAnimate += it
                                    delay(10)
                                }
                            }
                            ChatContainer(
                                text = textToAnimate,
                                isAssistant = true
                            )

                        }

                    }
                }
            }
            items(restaurants.size ?: 0) {
                val restaurantList = restaurants[it]

                restaurantList?.forEach {
                    val restaurant = it
                    if (restaurant != null) {
                        RestaurantContainer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(225.dp)
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                            textModifier = Modifier.padding(start = 16.dp),
                            image = restaurant.imageUrl,
                            name = restaurant.name,
                            category = restaurant.category,
                            mimOrder = restaurant.minOrder,
                            deliveryFee = restaurant.deliveryFee,
                            deliveryTime = restaurant.deliveryTime,
                            isVerified = restaurant.isVerified,
                        )
                    }
                }

            }
        }
        if (thinking){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        MessageField(
            navController = navController,
            onAddClick = {},
            onSendClick = {
                chatViewModel.setRequest(it)
            },
            onMessageChange = {},
            onMicroClick = {},
            unEnbled = thinking
        )
    }
}




@Composable
fun BoxScope.MessageField(
    navController: NavController,
    onAddClick: ()-> Unit = {},
    onSendClick: (String)-> Unit = {},
    onMessageChange: (String)-> Unit = {},
    onMicroClick: ()-> Unit = {},
    unEnbled: Boolean = false

) {
    var text by remember { mutableStateOf("") }
    // android:windowSoftInputMode="adjustResize" in <Activity> in AndroidManifest.xml is required for this to work
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Unspecified)
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
            value = text,
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
                    enabled = text.isNotEmpty() || unEnbled,
                ) {
                    onSendClick(text)
                    text = ""
                }
        )
    }



}

@Composable
fun ChatContainer(
    text: String ,
    isAssistant: Boolean = false
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Unspecified)
            .padding(end = 10.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ){
        Card(
            modifier = Modifier
                .height(34.dp)
                .width(34.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(Color.Unspecified),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            if (isAssistant){
                Icon(
                    painter = painterResource(id = R.drawable.bro),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    tint = Color.Unspecified
                )
            }else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                )
            }
        }
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 15.dp),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomEnd = 15.dp,
                bottomStart = 15.dp,
                topEnd = 15.dp
            ),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
            border = BorderStroke(1.dp, Color.Black)

        ){
            SelectionContainer {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(10.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Black
                    )
                )
            }
        }
    }
}


@Preview
@Composable
private fun _ChatContainer() {
    CheicksaTheme {
        ChatContainer(
            text = "Hello",
            isAssistant = true
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
