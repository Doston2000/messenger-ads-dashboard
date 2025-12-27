package uz.codingtech.messengerdashboard.presentation.orders

sealed class OrdersEvent {
    object LoadOrders : OrdersEvent()
    object Logout : OrdersEvent()
}