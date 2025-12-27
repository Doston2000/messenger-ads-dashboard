package uz.codingtech.messengerdashboard.data.repository

import kotlinx.coroutines.flow.first
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.Token
import uz.codingtech.messengerdashboard.domain.models.User
import uz.codingtech.messengerdashboard.domain.repository.AuthRepository
import uz.codingtech.messengerdashboard.utils.ErrorType
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val apiService: ApiService,
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

    override suspend fun checkToken(accessToken: String): UiResult<String>{
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.checkToken(Token(accessToken))
            if (response.isSuccessful) {
                UiResult.Success(response.body()?.status!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        }catch (e: Exception){
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun refreshToken(refreshToken: String): UiResult<String>{
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.refreshToken(RefreshToken(refreshToken))
            if (response.isSuccessful) {
                UiResult.Success(response.body()?.access!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        }catch (e: Exception){
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun login(user: User): UiResult<AuthData>{
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.login(user)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        }catch (e: Exception){
            UiResult.Error(ErrorType.UnExpected)
        }
    }
}
