package uz.codingtech.messengerdashboard.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import uz.codingtech.messengerdashboard.domain.models.AccessToken
import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.ActiveModelForPost
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPostModel
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.ResultStatus
import uz.codingtech.messengerdashboard.domain.models.Token
import uz.codingtech.messengerdashboard.domain.models.UploadMedia
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
    ): Response<ChatOrderPageModel>

    @GET("orders/{id}/")
    suspend fun getOrderById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ChatOrderModel>

    @POST("orders/{id}/cancel/")
    suspend fun cancelOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Any>

    @POST("channel_id-order/create/")
    suspend fun postChatOrder(
        @Header("Authorization") token: String,
        @Body postOrderModel: PostOrderChatModel
    ): Response<Any>

    @POST("orders/status/")
    suspend fun changeActive(
        @Header("Authorization") token: String,
        @Body activeModel: ActiveModel
    ): Response<ActiveModel>

    @Multipart
    @POST("chat_post/order/add_media/")
    suspend fun uploadMedia(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<UploadMedia>

    @POST("chat_post/order/create/")
    suspend fun postOrderPost(
        @Header("Authorization") token: String,
        @Body postOrderModel: PostOrderPostModel
    ): Response<Any>

    @GET("chat_post/orders/all/")
    suspend fun getPostOrders(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): Response<PostOrderPageModel>

    @GET("chat_post/order/{order_id}/detail/")
    suspend fun getPostOrderById(
        @Header("Authorization") token: String,
        @Path("order_id") order_id: String
    ): Response<PostOrderModel>

    @POST("chat_post/order/{order_id}/cancel/")
    suspend fun cancelPostOrder(
        @Header("Authorization") token: String,
        @Path("order_id") order_id: String
    ): Response<Any>

    @POST("chat_post/order/change_status/")
    suspend fun changePostActive(
        @Header("Authorization") token: String,
        @Body activeModel: ActiveModelForPost
    ): Response<Any>

}