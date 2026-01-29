package uz.codingtech.messengerdashboard.domain.models

data class ChatOrderPageModel(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<ChatOrderModel>
)