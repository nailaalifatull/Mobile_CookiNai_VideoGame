package com.cookinai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cookinai.app.ui.screen.DetailScreen
import com.cookinai.app.ui.screen.HomeScreen
import com.cookinai.app.ui.theme.CookiNaiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookiNaiTheme {
                CookiNaiApp()
            }
        }
    }
}

@Composable
fun CookiNaiApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onGameClick = { gameId ->
                    navController.navigate("detail/$gameId")
                }
            )
        }

        composable(
            route = "detail/{gameId}",
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: return@composable
            DetailScreen(
                gameId = gameId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
