package uz.codingtech.messengerdashboard.domain.usecase.order

import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.OrderModel
import uz.codingtech.messengerdashboard.domain.models.OrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.domain.repository.OrderRepository
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(page: Int): UiResult<OrderPageModel> {
        return repository.getOrders(page)
    }
}

class PostOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(postOrder: PostOrderModel): UiResult<String> {
        return repository.postOrder(postOrder)
    }
}

class GetOrderByIdUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(id: Int): UiResult<OrderModel> {
        return repository.getOrderById(id)
    }
}

class CancelOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(id: Int): UiResult<Any> {
        return repository.cancelOrder(id)
    }
}

class ChangeActiveOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(activeModel: ActiveModel): UiResult<ActiveModel> {
        return repository.changeActive(activeModel)
    }
}