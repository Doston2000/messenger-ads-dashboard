package uz.codingtech.messengerdashboard.presentation.dialog_order.chat_orders

sealed class ChatOrdersEvent {
    object LoadOrders : ChatOrdersEvent()
    object Logout : ChatOrdersEvent()
}