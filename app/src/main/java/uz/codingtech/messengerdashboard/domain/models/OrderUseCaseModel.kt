package uz.codingtech.messengerdashboard.domain.models

import uz.codingtech.messengerdashboard.domain.usecase.order.CancelOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.ChangeActiveOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.GetOrderByIdUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.GetOrdersUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.PostOrderUseCase

data class OrderUseCaseModel(
    val getOrdersUseCase: GetOrdersUseCase,
    val postOrderUseCase: PostOrderUseCase,
    val getOrderByIdUseCase: GetOrderByIdUseCase,
    val cancelOrderUseCase: CancelOrderUseCase,
    val changeActiveOrderUseCase: ChangeActiveOrderUseCase
)
