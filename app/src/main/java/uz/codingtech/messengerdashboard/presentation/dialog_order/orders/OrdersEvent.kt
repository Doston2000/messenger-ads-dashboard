package uz.codingtech.messengerdashboard.presentation.dialog_order.orders

sealed class OrdersEvent {
    object LoadOrders : OrdersEvent()
    object Logout : OrdersEvent()
}