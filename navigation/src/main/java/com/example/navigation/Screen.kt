package com.example.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Main : Screen("main")
    object Favorite : Screen("favorite")
    object Account : Screen("account")
    object CourseDetail : Screen("course_detail")
    companion object {
        val mainScreens = listOf(Main, Favorite, Account)
    }
}