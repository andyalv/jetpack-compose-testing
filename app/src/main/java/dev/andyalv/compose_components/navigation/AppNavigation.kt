package dev.andyalv.compose_components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.andyalv.compose_components.food_item.FoodItemDetailScreen
import dev.andyalv.compose_components.menu.MainScreen
import java.util.UUID

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.MenuScreen.route) {
        composable(route = AppScreens.MenuScreen.route) {
            MainScreen(navController = navController)
        }

        composable(route = (AppScreens.ItemScreen.route+"/{itemId}")) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")!!
            val itemUUID = UUID.fromString(itemId)

            FoodItemDetailScreen(itemId = itemUUID)
        }
    }
}