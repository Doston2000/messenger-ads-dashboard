package uz.codingtech.messengerdashboard.domain.repository

import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.User
import uz.codingtech.messengerdashboard.utils.UiResult

interface AuthRepository {
    suspend fun saveAuth(authData: AuthData)
    suspend fun getAuth(): AuthData?
    suspend fun clearAuth()
    suspend fun checkToken(accessToken: String): UiResult<String>
    suspend fun refreshToken(refreshToken: String): UiResult<String>
    suspend fun login(user: User): UiResult<AuthData>
}
