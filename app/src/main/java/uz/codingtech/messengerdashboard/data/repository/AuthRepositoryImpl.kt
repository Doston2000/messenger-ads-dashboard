package uz.codingtech.messengerdashboard.data.repository

import kotlinx.coroutines.flow.first
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.User
import uz.codingtech.messengerdashboard.domain.repository.AuthRepository
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

    override suspend fun checkToken(token: String) {
        apiService.checkToken("Bearer $token") // API request, agar xato bo‘lsa exception throw qiladi
    }

    override suspend fun login(user: User): AuthData {
        return apiService.login(user)
    }
}
