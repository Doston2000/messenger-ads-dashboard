package uz.codingtech.messengerdashboard.presentation.login

sealed class LoginEvent {
    data class Login(val username: String, val password: String) : LoginEvent()
}