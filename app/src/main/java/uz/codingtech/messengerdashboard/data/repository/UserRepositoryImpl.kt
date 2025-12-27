package uz.codingtech.messengerdashboard.data.repository

import kotlinx.coroutines.flow.first
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.repository.UserRepository
import uz.codingtech.messengerdashboard.utils.ErrorType
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val apiService: ApiService,
) : UserRepository {

    override suspend fun getBalance(): UiResult<Balance>{
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.getBalance("Bearer $token")
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