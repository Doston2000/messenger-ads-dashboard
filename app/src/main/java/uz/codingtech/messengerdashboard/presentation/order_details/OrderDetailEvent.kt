package uz.codingtech.messengerdashboard.presentation.order_details

sealed class OrderDetailEvent {
    data class ChangeActive(val id: Int, val active: Boolean) : OrderDetailEvent()
    data class Cancel(val id: Int) : OrderDetailEvent()
    data class InitOrderDetail(val id: Int) : OrderDetailEvent()
}