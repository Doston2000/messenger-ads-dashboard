package uz.codingtech.messengerdashboard.presentation.post_order.add_post_order

import uz.codingtech.messengerdashboard.domain.models.UploadMedia
import uz.codingtech.messengerdashboard.utils.ErrorType

data class AddPostOrderUiState(
    var success: Any? = null,
    var errorMessage: ErrorType? = null,
    var successUploadMedia: UploadMedia? = null,
    var errorUploadMedia: ErrorType?=null,
    var loading: Boolean = false
)