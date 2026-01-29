package uz.codingtech.messengerdashboard.domain.models

import uz.codingtech.messengerdashboard.domain.usecase.chat_order.CancelChatOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.ChangeActiveChatOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.GetChatOrderByIdUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.GetChatOrdersUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.PostChatOrderUseCase

data class ChatOrderUseCaseModel(
    val getOrdersUseCase: GetChatOrdersUseCase,
    val postOrderUseCase: PostChatOrderUseCase,
    val getOrderByIdUseCase: GetChatOrderByIdUseCase,
    val cancelOrderUseCase: CancelChatOrderUseCase,
    val changeActiveOrderUseCase: ChangeActiveChatOrderUseCase
)
