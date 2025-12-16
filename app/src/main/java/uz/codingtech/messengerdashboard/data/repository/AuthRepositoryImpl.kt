package uz.codingtech.messengerdashboard.data.repository

import kotlinx.coroutines.flow.first
import retrofit2.Response
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.domain.models.AccessToken
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.Status
import uz.codingtech.messengerdashboard.domain.models.Token
import uz.codingtech.messengerdashboard.domain.models.User
import uz.codingtech.messengerdashboard.domain.repository.AuthRepository
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun saveAuth(authData: AuthData) {
        tokenDataStore.saveAuthData(authData)
    }

    override suspend fun getAuth(): AuthData? {
        return tokenDataStore.authData.first()
    }

    override suspend fun clearAuth() {
        tokenDataStore.clear()
    }

    override suspend fun checkToken(token: Token): Response<Status>? {
        return try {
            apiService.checkToken(token)
        }catch (e: Exception){
            null
        }
    }

    override suspend fun refreshToken(refreshLString: RefreshToken): Response<AccessToken>? {
        try {
            return apiService.refreshToken(refreshLString)
        }catch (e: Exception){
            return null
        }
    }

    override suspend fun login(user: User): Response<AuthData>? {
        try {
            val result = apiService.login(user)
            return result
        } catch (e: Exception) {
            return null
        }

    }
}
