package uz.codingtech.messengerdashboard.presentation.chat_order.chat_order_details

import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.utils.ErrorType

data class ChatOrderDetailUiState(
    var success: ChatOrderModel? = null,
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false
)