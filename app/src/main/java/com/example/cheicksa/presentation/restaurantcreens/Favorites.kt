package com.example.cheicksa.presentation.restaurantcreens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.presentation.viewmodels.RoomViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorites(
    navController: NavController,
    roomViewModel: RoomViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val orders = roomViewModel.orders().asFlow().collectAsState(initial = listOf()).value.filter { it.completed }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {

                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = it
        ){
        //display comppleted orders
        }
    }
}

@Preview
@Composable
fun _Favorites() {
    CheicksaTheme {
        Favorites(rememberNavController())
    }
}