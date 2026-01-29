package uz.codingtech.messengerdashboard.presentation.chat_order.add_chat_order

import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel

sealed class AddChatOrderEvent {
    data class PostOrder(val postOrderModel: PostOrderChatModel) : AddChatOrderEvent()
}