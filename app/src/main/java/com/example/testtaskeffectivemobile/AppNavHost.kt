package com.example.testtaskeffectivemobile

import android.view.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.auth.presenation.screen.AuthScreen
import com.example.coursedetail.presentation.CourseDetailScreen
import com.example.design.components.navigation.BottomNavigationBar
import com.example.design.theme.AppTheme
import com.example.main.presentation.MainScreen
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
    val isNavigating by viewModel.isNavigating.collectAsState()

    var isUserAuthenticated by remember { mutableStateOf(true) }

    val displaySelectedItem = remember(selectedItem, currentRoute) {
        if (currentRoute?.startsWith(Screen.CourseDetail.route) == true) {
            viewModel.getCurrentSelectedItem(currentRoute)
        } else {
            selectedItem
        }
    }
    LaunchedEffect(currentRoute, isNavigating) {
        if (!isNavigating) {
            viewModel.updateNavigationState(currentRoute)
        }
    }


    println("Current route: $currentRoute")
    println("Selected item: $selectedItem")
    println("Display selected item: $displaySelectedItem")

    AppTheme {
        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar) {
                    BottomNavigationBar(
                        selectedItem = displaySelectedItem,
                        onItemSelected = { item ->
                            if (selectedItem != item && !isNavigating) {
                                viewModel.onNavItemSelected(item) { navItem ->
                                    when (navItem) {
                                        NavItem.Main -> {
                                            navController.navigate(Screen.Main.route) {
                                                popUpTo(Screen.Main.route) { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        }
                                        NavItem.Favorite -> {
                                            navController.navigate(Screen.Favorite.route) {
                                                popUpTo(Screen.Main.route)
                                                launchSingleTop = true
                                            }
                                        }
                                        NavItem.Account -> {
                                            navController.navigate(Screen.Account.route) {
                                                popUpTo(Screen.Main.route)
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppTheme.colors.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = if (isUserAuthenticated) Screen.Main.route else Screen.Auth.route,
                ) {
                    composable(Screen.Auth.route) {
                        AuthScreen(
                            onAuthSuccess = {
                                isUserAuthenticated = true
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.Auth.route) { inclusive = true }
                                }
                            },
                            onNavigateToRegister = {

                            }
                        )
                    }

                    composable(Screen.Main.route) {
                        MainScreen(
                            onNavigateToCourse = { courseId ->
                                navController.navigate("${Screen.CourseDetail.route}/$courseId")
                            }
                        )
                    }

                    composable(Screen.Favorite.route) {
                        FavoriteScreen(
                            onNavigateToCourse = { courseId ->
                                navController.navigate("${Screen.CourseDetail.route}/$courseId")
                            }
                        )
                    }

                    composable(Screen.Account.route) {
                        AccountScreen()
                    }

                    composable(
                        route = "${Screen.CourseDetail.route}/{courseId}",
                        arguments = listOf(navArgument("courseId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val courseId = backStackEntry.arguments?.getInt("courseId") ?: return@composable
                        CourseDetailScreen(courseId = courseId, onNavigateBack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun AccountScreen() {

}

@Composable
fun FavoriteScreen(
    onNavigateToCourse: (Int) -> Unit
) {

}
