package dev.andyalv.compose_components.navigation

sealed class AppScreens(val route: String) {
    object MenuScreen: AppScreens("menu")
    object ItemScreen: AppScreens("item")
}