package uz.codingtech.messengerdashboard.domain.repository

import retrofit2.Response
import uz.codingtech.messengerdashboard.domain.models.Balance

interface UserRepository {

    suspend fun getBalance(): Response<Balance>?

}