package uz.codingtech.messengerdashboard.domain.models

data class UploadMedia(
    var id: String,
    var file: String,
    var media_type: String,
    var created_at: String,
    var is_linked: Boolean
)
