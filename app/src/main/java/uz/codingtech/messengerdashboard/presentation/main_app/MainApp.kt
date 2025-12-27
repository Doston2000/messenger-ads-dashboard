package uz.codingtech.messengerdashboard.presentation.main_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.codingtech.messengerdashboard.presentation.common.navigation.Orders
import uz.codingtech.messengerdashboard.presentation.common.navigation.Login
import uz.codingtech.messengerdashboard.presentation.common.navigation.MainNavGraph

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination
    val backgroundColor = when (currentRoute) {
        Orders -> Color(0xFFFFFFFF)
        else -> Color(0xFFFFFFFF)
    }

    Scaffold(
        containerColor = backgroundColor,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
//                .padding(innerPadding)
            ,
            color = Color.Transparent
        ) {
            MainNavGraph(navController, Login)
        }
    }
}