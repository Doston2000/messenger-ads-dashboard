package uz.codingtech.messengerdashboard.domain.models

data class ActiveModel(
    val order_id: Int,
    val is_active: Boolean
)

data class ActiveModelForPost(
    val order_id: String,
    val is_active: Boolean
)