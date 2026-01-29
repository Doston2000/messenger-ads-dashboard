package uz.codingtech.messengerdashboard.domain.models

data class PostOrderPageModel (
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<PostOrderModel>
)