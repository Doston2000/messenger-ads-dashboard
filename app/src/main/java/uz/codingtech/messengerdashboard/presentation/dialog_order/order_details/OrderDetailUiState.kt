package uz.codingtech.messengerdashboard.presentation.dialog_order.order_details

import uz.codingtech.messengerdashboard.domain.models.OrderModel
import uz.codingtech.messengerdashboard.utils.ErrorType

data class OrderDetailUiState(
    var success: OrderModel? = null,
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false
)