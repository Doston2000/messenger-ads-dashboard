package uz.codingtech.messengerdashboard.presentation.chat_order.chat_orders

import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.utils.ErrorType

data class ChatOrdersUiState(
    var successBalance: Balance? = null,
    var successOrders: List<ChatOrderModel> = emptyList(),
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false,
    var userId: String = ""
)