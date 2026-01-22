package uz.codingtech.messengerdashboard.presentation.dialog_order.add_chat_order

import uz.codingtech.messengerdashboard.utils.ErrorType

data class AddChatOrderUiState(
    var success: String = "",
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false
)