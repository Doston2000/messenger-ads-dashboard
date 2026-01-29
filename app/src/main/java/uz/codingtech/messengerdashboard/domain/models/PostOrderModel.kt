package uz.codingtech.messengerdashboard.domain.models

data class PostOrderModel(
    var order_id: String,
    var user_id: String,
    var order_name: String,
    var text: String,
    var link: String,
    var channels: String,
    var media_url: String,
    var spm: String,
    var budget: String,
    var total_views: Int,
    var clicks: Int,
    var max_views_per_user: Int,
    var shown_views: Int,
    var remaining_views: Int,
    var refund_amount: String,
    var completed: Boolean,
    var cancelled: Boolean,
    var is_active: Boolean,
    var created_at: String
)