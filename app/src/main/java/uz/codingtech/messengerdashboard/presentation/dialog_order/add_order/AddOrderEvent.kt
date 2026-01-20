package uz.codingtech.messengerdashboard.presentation.dialog_order.add_order

import uz.codingtech.messengerdashboard.domain.models.PostOrderModel

sealed class AddOrderEvent {
    data class PostOrder(val postOrderModel: PostOrderModel) : AddOrderEvent()
}