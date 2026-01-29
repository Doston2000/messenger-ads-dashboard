package uz.codingtech.messengerdashboard.presentation.chat_order.chat_order_details

sealed class ChatOrderDetailEvent {
    data class ChangeActive(val id: Int, val active: Boolean) : ChatOrderDetailEvent()
    data class Cancel(val id: Int) : ChatOrderDetailEvent()
    data class InitOrderDetail(val id: Int) : ChatOrderDetailEvent()
}