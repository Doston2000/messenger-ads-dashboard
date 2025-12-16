package uz.codingtech.messengerdashboard.domain.repository

import retrofit2.Response
import uz.codingtech.messengerdashboard.domain.models.AccessToken
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.Status
import uz.codingtech.messengerdashboard.domain.models.Token
import uz.codingtech.messengerdashboard.domain.models.User

interface AuthRepository {
    suspend fun saveAuth(authData: AuthData)
    suspend fun getAuth(): AuthData?
    suspend fun clearAuth()
    suspend fun checkToken(token: Token): Response<Status>?
    suspend fun refreshToken(refreshLString: RefreshToken): Response<AccessToken>?
    suspend fun login(user: User): Response<AuthData>?
}
