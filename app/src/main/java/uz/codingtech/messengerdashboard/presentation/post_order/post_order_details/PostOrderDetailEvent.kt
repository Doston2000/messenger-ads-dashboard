package uz.codingtech.messengerdashboard.presentation.post_order.post_order_details

sealed class PostOrderDetailEvent {
    data class ChangeActive(val id: String, val active: Boolean) : PostOrderDetailEvent()
    data class Cancel(val id: String) : PostOrderDetailEvent()
    data class InitOrderDetail(val id: String) : PostOrderDetailEvent()
}