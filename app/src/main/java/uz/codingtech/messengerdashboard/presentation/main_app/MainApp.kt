package uz.codingtech.messengerdashboard.presentation.main_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.codingtech.messengerdashboard.presentation.common.navigation.Home
import uz.codingtech.messengerdashboard.presentation.common.navigation.Login
import uz.codingtech.messengerdashboard.presentation.common.navigation.MainNavGraph
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel.AuthState.Authorized
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel.AuthState.Loading
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel.AuthState.Unauthorized

@Composable
fun MainApp(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination
    val backgroundColor = when (currentRoute) {
        Home -> Color(0xFFFFFFFF)
        else -> Color(0xFFFFFFFF)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    viewModel.checkLogin()
                }

                Lifecycle.Event.ON_RESUME -> {

                }

                Lifecycle.Event.ON_PAUSE -> {

                }

                Lifecycle.Event.ON_DESTROY -> {

                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

//    val authState by viewModel.authState.collectAsState()
//
//    val startRoute = when (authState) {
//        is Authorized -> Home
//        is Unauthorized -> Login
//        is Loading -> Login
//    }

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