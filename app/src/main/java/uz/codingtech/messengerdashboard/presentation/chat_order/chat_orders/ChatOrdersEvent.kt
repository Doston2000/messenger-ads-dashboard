package uz.codingtech.messengerdashboard.presentation.chat_order.chat_orders

sealed class ChatOrdersEvent {
    object LoadOrders : ChatOrdersEvent()
    object Logout : ChatOrdersEvent()
}