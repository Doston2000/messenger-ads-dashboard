package uz.codingtech.messengerdashboard.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uz.codingtech.messengerdashboard.presentation.add_order.AddOrder
import uz.codingtech.messengerdashboard.presentation.home.Home
import uz.codingtech.messengerdashboard.presentation.login.Login
import uz.codingtech.messengerdashboard.presentation.order_details.OrderDetails

@Serializable
object Login

@Serializable
object Home

@Serializable
object AddOrder

@Serializable
object OrderDetails

@Serializable
data class OrderDetailsInfo(val id: String)

@Composable
fun MainNavGraph(navController: NavHostController, startRoute: Any) {

    NavHost(navController = navController, startDestination = startRoute) {
        composable<Login> {
            Login(navController = navController)
        }
        composable<Home> {
            Home(navController = navController)
        }
        composable<AddOrder> {
            AddOrder(
                onBack = { navController.popBackStack() },
                onSave = { newOrder ->
                    navController.popBackStack()
                }
            )
        }
        composable<OrderDetailsInfo> { backStackEntry ->
            val channelId =
                backStackEntry.arguments?.getLong("id") ?: return@composable

            OrderDetails(channelId = channelId)
        }
    }
}