package com.example.testtaskeffectivemobile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.design.components.navigation.BottomNavigationBar
import com.example.navigation.MainViewModel
import com.example.navigation.NavItem
import com.example.navigation.Screen

@Composable
fun AppNavHost(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedItem by viewModel.selectedItem.collectAsState()
    val shouldShowBottomBar by viewModel.shouldShowBottomBar.collectAsState()

    // Update ViewModel with current route
    LaunchedEffect(currentRoute) {
        viewModel.updateNavigationState(currentRoute)
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar && selectedItem != null) {
                BottomNavigationBar(
                    selectedItem = selectedItem!!,
                    onItemSelected = { item ->
                        viewModel.onNavItemSelected(item) { navItem ->
                            when (navItem) {
                                NavItem.Main -> navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.Main.route) { inclusive = true }
                                }
                                NavItem.Favorite -> navController.navigate(Screen.Favorite.route) {
                                    popUpTo(Screen.Main.route) { inclusive = true }
                                }
                                NavItem.Account -> navController.navigate(Screen.Account.route) {
                                    popUpTo(Screen.Main.route) { inclusive = true }
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Auth.route) { AuthScreen() }
            composable(Screen.Main.route) { MainScreen() }
            composable(Screen.Favorite.route) { FavoriteScreen() }
            composable(Screen.Account.route) { AccountScreen() }
        }
    }
}

@Composable
fun AccountScreen() {

}

@Composable
fun FavoriteScreen() {

}

@Composable
fun MainScreen() {

}

@Composable
fun AuthScreen() {

}


