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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cheicksa.navigation.NavConstants
import com.example.cheicksa.navigation.bottomBar.BottomBarNavigation
import com.example.cheicksa.navigation.navhost.AppNavHost
import com.example.cheicksa.presentation.viewmodels.NetworkViewModel
import com.example.cheicksa.presentation.viewmodels.RoomViewModel
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.example.cheicksa.ui.theme.montSarrat
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
                        color = MaterialTheme.colorScheme.tertiary
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
    // if current destination is shop on va dire bottomBarNavigationItems = listOF(Home, Favorites, Cart, Profile)
    // pour shop donc comme ca va mettre les buttom bar de shop comme pour restaurant automatiquement on va
    // melanger les cart des shop et restaurant etc
    /// if (currentRoute?.hierarchy?.any { it.route == RESTAURANT_ROUTE } == true
    val bottomBarNavigationItems = listOf(
        BottomBarNavigation.Home,
        BottomBarNavigation.Favorites,
        // BottomBarNavigation.Camera,
        BottomBarNavigation.Cart,
        BottomBarNavigation.Profile,
    )
    val roomViewModel: RoomViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    if (currentRoute?.hierarchy?.any { it.route == NavConstants.AUTH_ROUTE } == false &&
        !currentRoute.hierarchy.any { it.route == NavConstants.HOME_SCREEN_ROUTE }
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
                val isSelected = currentRoute.hierarchy.any { it.route == bottomBarNavigation.route }
                val badgeNum = roomViewModel.orders().asFlow().collectAsState(initial = null).value?.size
                BottomBarItem(
                    navController = navController,
                    bottomBarNavigation = bottomBarNavigation,
                    isSelected = isSelected,
                    applyBadge = bottomBarNavigation == BottomBarNavigation.Cart && badgeNum != null,
                    badgeNum = badgeNum ?: 0
                )
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.BottomBarItem(
    navController: NavController,
    bottomBarNavigation: BottomBarNavigation,
    isSelected: Boolean,
    applyBadge: Boolean = false,
    badgeNum: Int = 0,
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
            BadgedBox(
                badge = {
                    if (applyBadge && badgeNum != 0)
                        Card(
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                            modifier = Modifier
                        ) {
                            Text(
                                text = badgeNum.coerceAtMost(99).toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(horizontal= 3.dp)
                                    .padding(2.dp),
                                fontFamily = montSarrat,
                            )
                        }
                } ,
                modifier = Modifier
            ) {
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
        //BottomBar(navController = rememberNavController())
    }
}