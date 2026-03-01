package com.example.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedItem = MutableStateFlow<NavItem?>(null)
    val selectedItem: StateFlow<NavItem?> = _selectedItem.asStateFlow()

    private val _shouldShowBottomBar = MutableStateFlow(false)
    val shouldShowBottomBar: StateFlow<Boolean> = _shouldShowBottomBar.asStateFlow()

    private val _isNavigating = MutableStateFlow(false)
    val isNavigating: StateFlow<Boolean> = _isNavigating.asStateFlow()

    fun updateNavigationState(currentRoute: String?) {
        if (_isNavigating.value) return

        val newSelectedItem = NavItem.fromRoute(currentRoute)

        if (_selectedItem.value != newSelectedItem) {
            _selectedItem.value = newSelectedItem
        }

        _shouldShowBottomBar.value = currentRoute in listOf(
            Screen.Main.route,
            Screen.Favorite.route,
            Screen.Account.route
        )
    }

    fun onNavItemSelected(item: NavItem, onNavigate: (NavItem) -> Unit) {
        viewModelScope.launch {

            _isNavigating.value = true


            _selectedItem.value = null

            onNavigate(item)

            delay(300)

            _isNavigating.value = false


            _selectedItem.value = item
        }
    }
}