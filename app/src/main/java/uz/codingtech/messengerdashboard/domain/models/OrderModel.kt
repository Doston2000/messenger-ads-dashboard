package uz.codingtech.messengerdashboard.domain.models

import com.google.gson.annotations.SerializedName

data class OrderModel(
//    @SerializedName("")
    var budget: String,
    var cancelled: Boolean,
    var channel_id: Int,
    var channel_name: String,
    var completed: Boolean,
    var created_at: String,
    var id: Int,
    var is_active: Boolean,
    var order_name: String,
    var refund_amount: String,
    var remaining_views: Int,
    var shown_views: Int,
    var spm: String,
    var tags: List<String>,
    var total_views: Int,
    var max_view_per_user: Int
)