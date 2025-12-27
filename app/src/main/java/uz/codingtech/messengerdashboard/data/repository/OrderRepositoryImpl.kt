package uz.codingtech.messengerdashboard.data.repository

import kotlinx.coroutines.flow.first
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.OrderModel
import uz.codingtech.messengerdashboard.domain.models.OrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.domain.repository.OrderRepository
import uz.codingtech.messengerdashboard.utils.ErrorType
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val apiService: ApiService,
) : OrderRepository {

    override suspend fun getOrders(page: Int): UiResult<OrderPageModel> {
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.getOrders("Bearer $token", page = page)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        }catch (e: Exception){
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun postOrder(postOrderModel: PostOrderModel): UiResult<String> {
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.postOrder("Bearer $token", postOrderModel)
            if (response.isSuccessful) {
                UiResult.Success("Success")
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        }catch (e: Exception){
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun getOrderById(id: Int): UiResult<OrderModel>{
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.getOrderById("Bearer $token", id)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        }catch (e: Exception){
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun cancelOrder(id: Int): UiResult<Any>{
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.cancelOrder("Bearer $token", id)
            if (response.isSuccessful) {
                UiResult.Success(response.body()!!)
            } else {
                UiResult.Error(ErrorType.BadRequest)
            }
        }catch (e: Exception){
            UiResult.Error(ErrorType.UnExpected)
        }
    }

    override suspend fun changeActive(activeModel: ActiveModel): UiResult<ActiveModel>{
        val token = tokenDataStore.authData.first()?.access
        if (token == null) {
            return UiResult.Error(ErrorType.UnAuthorized)
        }
        return try {
            val response = apiService.changeActive("Bearer $token", activeModel)
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