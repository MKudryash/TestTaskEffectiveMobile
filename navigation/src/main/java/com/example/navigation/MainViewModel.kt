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

    // Сохраняем предыдущий выбранный элемент для экранов деталей
    private var lastSelectedItem: NavItem? = null

    fun updateNavigationState(currentRoute: String?) {
        if (_isNavigating.value) return

        val newSelectedItem = NavItem.fromRoute(currentRoute)

        if (currentRoute?.startsWith(Screen.CourseDetail.route) == true) {

            if (lastSelectedItem == null) {
                lastSelectedItem = _selectedItem.value
            }

        } else {

            if (_selectedItem.value != newSelectedItem) {
                _selectedItem.value = newSelectedItem
            }

            if (currentRoute in listOf(Screen.Main.route, Screen.Favorite.route, Screen.Account.route)) {
                lastSelectedItem = null
            }
        }


        _shouldShowBottomBar.value = when {
            currentRoute == null -> false
            currentRoute == Screen.Main.route -> true
            currentRoute == Screen.Favorite.route -> true
            currentRoute == Screen.Account.route -> true
            currentRoute.startsWith(Screen.CourseDetail.route) -> true
            else -> false
        }
    }

    fun onNavItemSelected(item: NavItem, onNavigate: (NavItem) -> Unit) {
        viewModelScope.launch {
            _isNavigating.value = true


            _selectedItem.value = item
            lastSelectedItem = item

            onNavigate(item)

            delay(300)
            _isNavigating.value = false
        }
    }


    fun getCurrentSelectedItem(currentRoute: String?): NavItem? {
        return if (_shouldShowBottomBar.value && currentRoute?.startsWith(Screen.CourseDetail.route) == true) {

            lastSelectedItem ?: _selectedItem.value
        } else {
            _selectedItem.value
        }
    }
}