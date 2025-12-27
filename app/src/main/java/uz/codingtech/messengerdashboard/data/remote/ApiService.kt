package uz.codingtech.messengerdashboard.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uz.codingtech.messengerdashboard.domain.models.AccessToken
import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.models.OrderModel
import uz.codingtech.messengerdashboard.domain.models.OrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.ResultStatus
import uz.codingtech.messengerdashboard.domain.models.Token
import uz.codingtech.messengerdashboard.domain.models.User

interface ApiService {

    @POST("auth/login/")
    suspend fun login(@Body user: User): Response<AuthData>

    @POST("auth/verify/")
    suspend fun checkToken(
        @Body token: Token
    ): Response<ResultStatus>

    @POST("auth/refresh/")
    suspend fun refreshToken(
        @Body refresh: RefreshToken
    ): Response<AccessToken>

    @GET("balance/")
    suspend fun getBalance(
        @Header("Authorization") token: String
    ): Response<Balance>

    @GET("orders/all/")
    suspend fun getOrders(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): Response<OrderPageModel>

    @GET("orders/{id}/")
    suspend fun getOrderById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<OrderModel>

    @POST("orders/{id}/cancel/")
    suspend fun cancelOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Any>

    @POST("channel_id-order/create/")
    suspend fun postOrder(
        @Header("Authorization") token: String,
        @Body postOrderModel: PostOrderModel
    ): Response<Any>

    @POST("orders/status/")
    suspend fun changeActive(
        @Header("Authorization") token: String,
        @Body activeModel: ActiveModel
    ): Response<ActiveModel>

}