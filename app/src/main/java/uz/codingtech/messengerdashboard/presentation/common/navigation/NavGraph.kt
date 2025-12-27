package uz.codingtech.messengerdashboard.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uz.codingtech.messengerdashboard.presentation.add_order.AddOrder
import uz.codingtech.messengerdashboard.presentation.orders.Orders
import uz.codingtech.messengerdashboard.presentation.login.Login
import uz.codingtech.messengerdashboard.presentation.order_details.OrderDetails

@Serializable
object Login

@Serializable
object Orders

@Serializable
object AddOrder

@Serializable
object OrderDetails

@Serializable
data class OrderDetailsInfo(val id: Int)

@Composable
fun MainNavGraph(navController: NavHostController, startRoute: Any) {

    NavHost(navController = navController, startDestination = startRoute) {
        composable<Login> {
            Login(navController = navController)
        }
        composable<Orders> {
            Orders(navController = navController)
        }
        composable<AddOrder> {
            AddOrder(navController = navController)
        }
        composable<OrderDetailsInfo> { backStackEntry ->
            val orderId =
                backStackEntry.arguments?.getInt("id") ?: return@composable

            OrderDetails(navController = navController, orderId = orderId)
        }
    }
}