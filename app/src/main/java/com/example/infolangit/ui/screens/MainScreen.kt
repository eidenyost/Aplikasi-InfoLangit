package com.example.infolangit.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.infolangit.R
import com.example.infolangit.ui.theme.AppColors

// Define navigation routes
sealed class Screen(val route: String, val title: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Filled.Home)
    object Weather : Screen("weather", R.string.weather, Icons.Filled.WbSunny)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        // Use NavHost to manage navigation
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                BackgroundScreen()
            }
            composable(Screen.Weather.route) {
                WeatherScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Define the screens for bottom navigation
    val screens = listOf(Screen.Home, Screen.Weather)

    // Track the current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = AppColors.Surface,
        contentColor = AppColors.Primary
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(screen.title)
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.title)
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.Primary,
                    selectedTextColor = AppColors.Primary,
                    unselectedIconColor = AppColors.TextSecondary,
                    unselectedTextColor = AppColors.TextSecondary
                )
            )
        }
    }
}