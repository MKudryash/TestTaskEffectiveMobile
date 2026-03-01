package com.example.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedItem = MutableStateFlow<NavItem?>(null)
    val selectedItem: StateFlow<NavItem?> = _selectedItem.asStateFlow()

    private val _shouldShowBottomBar = MutableStateFlow(false)
    val shouldShowBottomBar: StateFlow<Boolean> = _shouldShowBottomBar.asStateFlow()

    fun updateNavigationState(currentRoute: String?) {
        _selectedItem.value = when (currentRoute) {
            Screen.Main.route -> NavItem.Main
            Screen.Favorite.route -> NavItem.Favorite
            Screen.Account.route -> NavItem.Account
            else -> null
        }

        _shouldShowBottomBar.value = currentRoute in listOf(
            Screen.Main.route,
            Screen.Favorite.route,
            Screen.Account.route
        )
    }

    fun onNavItemSelected(item: NavItem, onNavigate: (NavItem) -> Unit) {
        onNavigate(item)
    }
}