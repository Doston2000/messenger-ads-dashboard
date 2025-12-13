package uz.codingtech.messengerdashboard.domain.models

data class AuthData(
    val accessToken: String = "",
    val refreshToken: String = "",
    val token: String = "",
    val username: String = ""
)