package uz.codingtech.messengerdashboard.presentation.add_order

import uz.codingtech.messengerdashboard.utils.ErrorType

data class AddOrderUiState(
    var success: String = "",
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false
)