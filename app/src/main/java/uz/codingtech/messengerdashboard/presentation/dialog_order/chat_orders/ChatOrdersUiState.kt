package uz.codingtech.messengerdashboard.presentation.dialog_order.chat_orders

import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.models.OrderModel
import uz.codingtech.messengerdashboard.utils.ErrorType

data class ChatOrdersUiState(
    var successBalance: Balance? = null,
    var successOrders: List<OrderModel> = emptyList(),
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false,
    var userId: String = ""
)