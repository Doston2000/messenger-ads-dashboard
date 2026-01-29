package uz.codingtech.messengerdashboard.presentation.post_order.post_orders

sealed class PostOrdersEvent {
    object LoadOrders : PostOrdersEvent()
}