package uz.codingtech.messengerdashboard.presentation.post_order.post_orders

import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.utils.ErrorType

data class PostOrdersUiState(
    var successBalance: Balance? = null,
    var successOrders: List<PostOrderModel> = emptyList(),
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false,
)