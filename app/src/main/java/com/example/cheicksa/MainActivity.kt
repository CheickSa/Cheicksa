package com.example.cheicksa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.navigation.navhost.AppNavHost
import com.example.cheicksa.presentation.viewmodels.NetworkViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheicksaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause: ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy: ")
    }
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop: ")

    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart: ")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("MainActivity", "onLowMemory: ")
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d("MainActivity", "onUserInteraction: ")
    }



}

@Composable
fun Greeting(
    viewModel: NetworkViewModel
) {
    val stores by remember { viewModel.allStores }.collectAsState()
    viewModel.getAllStore()


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val viewModel = hiltViewModel<NetworkViewModel>()
    CheicksaTheme {
        Greeting(viewModel)
    }
}