package uz.codingtech.messengerdashboard.domain.usecase.chat_order

import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel
import uz.codingtech.messengerdashboard.domain.repository.ChatOrderRepository
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

class GetChatOrdersUseCase @Inject constructor(
    private val repository: ChatOrderRepository
) {
    suspend operator fun invoke(page: Int): UiResult<ChatOrderPageModel> {
        return repository.getOrders(page)
    }
}

class PostChatOrderUseCase @Inject constructor(
    private val repository: ChatOrderRepository
) {
    suspend operator fun invoke(postOrder: PostOrderChatModel): UiResult<String> {
        return repository.postOrder(postOrder)
    }
}

class GetChatOrderByIdUseCase @Inject constructor(
    private val repository: ChatOrderRepository
) {
    suspend operator fun invoke(id: Int): UiResult<ChatOrderModel> {
        return repository.getOrderById(id)
    }
}

class CancelChatOrderUseCase @Inject constructor(
    private val repository: ChatOrderRepository
) {
    suspend operator fun invoke(id: Int): UiResult<Any> {
        return repository.cancelOrder(id)
    }
}

class ChangeActiveChatOrderUseCase @Inject constructor(
    private val repository: ChatOrderRepository
) {
    suspend operator fun invoke(activeModel: ActiveModel): UiResult<ActiveModel> {
        return repository.changeActive(activeModel)
    }
}