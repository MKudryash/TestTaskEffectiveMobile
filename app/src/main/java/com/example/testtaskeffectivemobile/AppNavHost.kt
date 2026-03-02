package com.example.testtaskeffectivemobile

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.auth.presenation.screen.AuthScreen
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

    LaunchedEffect(currentRoute, isNavigating) {
        if (!isNavigating) {
            viewModel.updateNavigationState(currentRoute)
        }
    }

    println("Selected item: $selectedItem")

    AppTheme {
        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar) {
                    BottomNavigationBar(
                        selectedItem = selectedItem,
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
                    modifier = Modifier.padding(paddingValues)
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
                                // Навигация на экран регистрации
                                // navController.navigate(Screen.Register.route)
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
                        CourseDetailScreen(courseId = courseId)
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

@Composable
fun CourseDetailScreen(
    courseId: Int
) {

}