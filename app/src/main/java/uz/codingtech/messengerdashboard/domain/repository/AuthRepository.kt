package uz.codingtech.messengerdashboard.domain.repository

import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.User

interface AuthRepository {
    suspend fun saveAuth(authData: AuthData)
    suspend fun getAuth(): AuthData?
    suspend fun clearAuth()
    suspend fun checkToken(token: String)
    suspend fun login(user: User): AuthData
}
