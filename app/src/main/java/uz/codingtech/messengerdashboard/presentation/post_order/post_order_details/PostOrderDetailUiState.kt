package uz.codingtech.messengerdashboard.presentation.post_order.post_order_details

import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.utils.ErrorType

data class PostOrderDetailUiState(
    var success: PostOrderModel? = null,
    var errorMessage: ErrorType? = null,
    var loading: Boolean = false
)