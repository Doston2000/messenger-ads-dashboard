package uz.codingtech.messengerdashboard.presentation.main_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.codingtech.messengerdashboard.presentation.common.navigation.MainNavGraph
import uz.codingtech.messengerdashboard.presentation.common.navigation.Screen
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel.AuthState.Authorized
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel.AuthState.Loading
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel.AuthState.Unauthorized

@Composable
fun MainApp(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val backgroundColor = when (currentRoute) {
        Screen.Home.route -> Color(0xFFFFFFFF)
        else -> Color(0xFFFFFFFF)
    }

    val authState by viewModel.authState.collectAsState()

    val startRoute = when (authState) {
        is Authorized -> Screen.Home.route
        is Unauthorized -> Screen.Login.route
        is Loading -> Screen.Login.route
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
            MainNavGraph(navController, startRoute)
        }
    }
}