package uz.codingtech.messengerdashboard.presentation.menu

import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.utils.ErrorType

data class MenuUiState(
    var successBalance: Balance? = null,
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false,
    var userId: String = ""
)