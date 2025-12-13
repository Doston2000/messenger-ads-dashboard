package uz.codingtech.messengerdashboard.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.codingtech.messengerdashboard.presentation.home.Home
import uz.codingtech.messengerdashboard.presentation.login.Login

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
}

@Composable
fun MainNavGraph(navController: NavHostController, startRoute: String) {
    NavHost(navController = navController, startDestination = startRoute) {
        composable(route = Screen.Login.route) {
            Login(navController = navController)
        }
        composable(route = Screen.Home.route) {
            Home(navController = navController)
        }
    }
}