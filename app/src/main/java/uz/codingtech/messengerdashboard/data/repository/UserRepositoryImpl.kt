package uz.codingtech.messengerdashboard.data.repository

import kotlinx.coroutines.flow.first
import retrofit2.Response
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getBalance(): Response<Balance>? {
        val token = tokenDataStore.authData.first()?.access
        return if (token != null){
            apiService.getBalance("Bearer $token")
        }else{
            null
        }
    }


}