package uz.codingtech.messengerdashboard.domain.models

data class Order(
    val title: String,
    val channelId: Long,
    val channelUsername: String,
    val cpm: String,
    val budget: String,
    val isCompleted: Boolean,
    val isActive: Boolean,
    var viewCount: Int,
    var viewedCount: Int,
    val createdDate: String
)