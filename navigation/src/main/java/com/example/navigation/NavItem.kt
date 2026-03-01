package com.example.navigation

sealed class NavItem(val title: String, val route: String) {
    object Main : NavItem("Главная", Screen.Main.route)
    object Favorite : NavItem("Избранное", Screen.Favorite.route)
    object Account : NavItem("Аккаунт", Screen.Account.route)

    companion object {
        val items = listOf(Main, Favorite, Account)

        fun fromRoute(route: String?): NavItem? {
            return items.find { it.route == route }
        }
    }
}