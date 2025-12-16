package uz.codingtech.messengerdashboard.domain.models

data class AuthData(
    var refresh: String = "",
    var access: String = "",
    var username: String = "",
    var user_id: String = "",
    var is_admin: Boolean = false
)