package uz.codingtech.messengerdashboard.domain.models

data class PostOrderPostModel(
    val order_name: String,
    val text: String,
    val link: String,
    val channels: String,
    val spm: String,
    val budget: String,
    val max_views_per_user: Int,
    val is_active: Boolean,
    val media_id: String?
)