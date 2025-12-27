package uz.codingtech.messengerdashboard.presentation.login

import uz.codingtech.messengerdashboard.utils.ErrorType

data class LoginUiState(
    var success: Boolean = false,
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false
)
