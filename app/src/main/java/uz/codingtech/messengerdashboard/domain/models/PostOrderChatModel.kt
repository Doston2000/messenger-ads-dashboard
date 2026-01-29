package uz.codingtech.messengerdashboard.domain.models

data class PostOrderChatModel(
    val channel_id:String,
    val channel_name: String,
    val tags: ArrayList<String>,
    val order_name:String,
    val spm: String,
    val budget: String,
    val max_views_per_user:Int,
    val is_active: Boolean
)
