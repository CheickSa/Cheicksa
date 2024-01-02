package com.example.cheicksa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.bottomBar.BottomBarNavigation
import com.example.cheicksa.navigation.navhost.AppNavHost
import com.example.cheicksa.presentation.viewmodels.NetworkViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CheicksaTheme {
                Scaffold (
                    bottomBar = {
                        BottomBar(navController = navController)
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost(navController = navController)
                    }
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
fun BottomBar(
    navController: NavController,
) {
    val bottomBarNavigationItems = listOf(
        BottomBarNavigation.Home,
        BottomBarNavigation.Favorites,
        // BottomBarNavigation.Camera,
        BottomBarNavigation.Cart,
        BottomBarNavigation.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    if (currentRoute?.hierarchy?.any { it.route == NavConstants.AUTH_ROUTE } == false &&
        currentRoute?.hierarchy?.any { it.route == NavConstants.HOME_SCREEN_ROUTE } == false
    ) {
        NavigationBar(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                .height(75.dp),
            contentColor = Color.Unspecified,
            containerColor = MaterialTheme.colorScheme.tertiary,
        ) {
            bottomBarNavigationItems.forEach { bottomBarNavigation ->
                val isSelected =
                    currentRoute?.hierarchy?.any { it.route == bottomBarNavigation.route } == true
                BottomBarItem(
                    navController = navController,
                    bottomBarNavigation = bottomBarNavigation,
                    isSelected = isSelected,
                )
            }
        }
    }

}
@Composable
fun RowScope.BottomBarItem(
    navController: NavController,
    bottomBarNavigation: BottomBarNavigation,
    isSelected: Boolean,
) {
    val scope = rememberCoroutineScope()
    NavigationBarItem(
        modifier = Modifier
            .background(Color.Unspecified),
        selected = isSelected,
        onClick = {
            scope.launch {
                navController.navigate(bottomBarNavigation.route){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                    anim {  }
                }
            }
        },
        icon = {
            val icon = if (!isSelected) bottomBarNavigation.iconOutlined else bottomBarNavigation.icon
            if (icon is ImageVector)
                Icon(
                    imageVector = icon,
                    contentDescription = bottomBarNavigation.title,
                )
            else if (icon is Int){
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = bottomBarNavigation.title,
                )
            }
        },
        label = { Text(bottomBarNavigation.title) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.primary,
            disabledIconColor = Color.Unspecified,
            disabledTextColor = Color.Unspecified,
            indicatorColor = MaterialTheme.colorScheme.tertiary,
        )
    )
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