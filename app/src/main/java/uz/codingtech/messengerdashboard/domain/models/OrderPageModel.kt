package uz.codingtech.messengerdashboard.domain.models

data class OrderPageModel(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<OrderModel>
)