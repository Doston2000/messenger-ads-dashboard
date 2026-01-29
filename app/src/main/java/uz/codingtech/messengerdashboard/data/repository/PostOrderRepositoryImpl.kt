package uz.codingtech.messengerdashboard.data.repository

import android.content.Context
import kotlinx.coroutines.flow.first
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.ActiveModelForPost
import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPostModel
import uz.codingtech.messengerdashboard.domain.models.UploadMedia
import uz.codingtech.messengerdashboard.domain.repository.PostOrderRepository
import uz.codingtech.messengerdashboard.utils.ErrorType
import uz.codingtech.messengerdashboard.utils.UiResult
import uz.codingtech.messengerdashboard.utils.toMultipart
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostOrderRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val apiService: ApiService,
    private val context: Context
) : PostOrderRepository {

    override suspend fun getOrders(page: Int): UiResult<PostOrderPageModel> {
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.getPostOrders("Bearer $token", page = page)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        } catch (e: Exception) {
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun postOrderMedia(file: File): UiResult<UploadMedia> {
        val part = file.toMultipart(context)
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.uploadMedia("Bearer $token", part)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        } catch (e: Exception) {
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun postOrder(postOrderModel: PostOrderPostModel): UiResult<Any> {
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.postOrderPost("Bearer $token", postOrderModel)
            if (response.isSuccessful) {
                UiResult.Success("Success")
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        } catch (e: Exception) {
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun getOrderById(id: String): UiResult<PostOrderModel> {
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.getPostOrderById("Bearer $token", id)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        } catch (e: Exception) {
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun cancelOrder(id: String): UiResult<Any> {
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.cancelPostOrder("Bearer $token", id)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        } catch (e: Exception) {
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun changeActive(activeModel: ActiveModelForPost): UiResult<Any> {
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.changePostActive("Bearer $token", activeModel)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        } catch (e: Exception) {
            UiResult.Error(ErrorType.UnExpected)
        }
    }
}