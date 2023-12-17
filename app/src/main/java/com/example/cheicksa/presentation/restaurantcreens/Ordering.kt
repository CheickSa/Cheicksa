package com.example.cheicksa.presentation.restaurantcreens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.Extra
import com.example.cheicksa.model.restaurant.OrderScreenCardData
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ordering(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel(LocalContext.current as ComponentActivity)
) {
    val data by menuViewModel.order.collectAsState()
    var amount = remember { mutableStateOf(1) }
    var instructions by remember { mutableStateOf("") }
    var add by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    var price by remember { mutableStateOf("0") }

    //if (data == null) { return }
    Scaffold (
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .align(Alignment.CenterVertically),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row (){
                        Icon(
                            painter = painterResource(id = R.drawable.plus_circle_outline),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .width(34.dp)
                                .height(34.dp)
                                .clickable { amount.value++ }
                        )
                        Text(
                            text = amount.value.toString(),
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.minus_circle_outline),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .width(34.dp)
                                .height(34.dp)
                                .clickable {
                                    if (amount.value > 1) {
                                        amount.value--
                                    }
                                }
                        )

                    }
                    Card(
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(top = 10.dp, bottom = 10.dp, end = 10.dp, start = 10.dp)
                            .align(Alignment.CenterVertically),
                        onClick = { add = !add }
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_to_cart),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center

                        )

                    }
                }
            }
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val image = data.imageUlr
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(image)
                                .build(),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize(),
                            error = painterResource(id = R.drawable.cheeseburger)
                        )
                    }

                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(10.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(50))
                            .align(Alignment.TopStart)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .align(Alignment.Center)
                            ,
                            tint = Color.Black
                        )
                    }

                }
                //Divider(modifier = Modifier.padding(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.title,
                        modifier = Modifier,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = data.price.toString() + " " + stringResource(id = R.string.devise) + " ",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )
                }
                Text(
                    text = data.description,
                    modifier = Modifier.padding(start = 10.dp , end = 40.dp),
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Divider(modifier = Modifier.padding(10.dp))
            }

            items(data.extra.size) {
                val extra = data.extra[it]
                if (data.extra.isNotEmpty()) {
                    Divider(modifier = Modifier.padding(10.dp))
                }
                var checked by remember { mutableStateOf(false) }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        modifier = Modifier
                            .height(16.dp)
                            .width(16.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = extra.name,
                        modifier = Modifier,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = extra.price + " " + stringResource(id = R.string.devise) + " ",
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )

                }

            }
            item {
                Divider(modifier = Modifier.padding(10.dp))
                Column(
                    modifier = Modifier.padding(start = 10.dp , end = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.special_instructions),
                        modifier = Modifier,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(id = R.string.special_instructions_description),
                        modifier = Modifier.padding( end = 10.dp),
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 1.2.em,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = instructions,
                        onValueChange = { instructions = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.special_instructions_label),
                                modifier = Modifier,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(10.dp),

                        )
                }
            }
        }
    }

}


@Preview
@Composable
private fun _Ordering() {
    CheicksaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Ordering(rememberNavController())
        }
    }
}