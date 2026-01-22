package uz.codingtech.messengerdashboard.presentation.dialog_order.add_chat_order

import uz.codingtech.messengerdashboard.domain.models.PostOrderModel

sealed class AddChatOrderEvent {
    data class PostOrder(val postOrderModel: PostOrderModel) : AddChatOrderEvent()
}