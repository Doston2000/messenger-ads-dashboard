package uz.codingtech.messengerdashboard.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uz.codingtech.messengerdashboard.presentation.dialog_order.add_chat_order.AddChatOrder
import uz.codingtech.messengerdashboard.presentation.dialog_order.chat_order_details.ChatOrderDetails
import uz.codingtech.messengerdashboard.presentation.dialog_order.chat_orders.ChatOrders
import uz.codingtech.messengerdashboard.presentation.login.Login
import uz.codingtech.messengerdashboard.presentation.menu.Menu
import uz.codingtech.messengerdashboard.presentation.post_order.add_post_order.AddPostOrder
import uz.codingtech.messengerdashboard.presentation.post_order.post_order_details.PostOrderDetails
import uz.codingtech.messengerdashboard.presentation.post_order.post_orders.PostOrders

@Serializable
object Login

@Serializable
object Menu

@Serializable
object ChatOrder

@Serializable
object AddChatOrder

@Serializable
data class OrderDetailsInfo(val id: Int)

@Serializable
object PostOrder

@Serializable
object AddPostOrder

@Serializable
data class PostOrderDetails(val id: Int)

@Composable
fun MainNavGraph(navController: NavHostController, startRoute: Any) {

    NavHost(navController = navController, startDestination = startRoute) {
        composable<Login> {
            Login(navController = navController)
        }
        composable<Menu> {
            Menu(navController = navController)
        }
        composable<ChatOrder> {
            ChatOrders(navController = navController)
        }
        composable<AddChatOrder> {
            AddChatOrder(navController = navController)
        }
        composable<OrderDetailsInfo> { backStackEntry ->
            val orderId =
                backStackEntry.arguments?.getInt("id") ?: return@composable

            ChatOrderDetails(navController = navController, orderId = orderId)
        }

        composable<PostOrder> {
            PostOrders(navController = navController)
        }
        composable<AddPostOrder> {
            AddPostOrder(navController = navController)
        }
        composable<PostOrderDetails> { backStackEntry ->
            val orderId =
                backStackEntry.arguments?.getInt("id") ?: return@composable

            PostOrderDetails(navController = navController, orderId = orderId)
        }
    }
}