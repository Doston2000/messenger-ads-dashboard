package uz.codingtech.messengerdashboard.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.codingtech.messengerdashboard.domain.models.AccessToken
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.Status
import uz.codingtech.messengerdashboard.domain.models.Token
import uz.codingtech.messengerdashboard.domain.models.User

interface ApiService {

    @POST("auth/login/")
    suspend fun login(@Body user: User): Response<AuthData>

    @POST("auth/verify/")
    suspend fun checkToken(
        @Body token: Token
    ): Response<Status>

    @POST("auth/refresh/")
    suspend fun refreshToken(
        @Body refresh: RefreshToken
    ): Response<AccessToken>

    @GET("balance/")
    suspend fun getBalance(
        @Header("Authorization") token: String
    ): Response<Balance>

}