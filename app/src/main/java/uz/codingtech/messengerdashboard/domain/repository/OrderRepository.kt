package uz.codingtech.messengerdashboard.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.OrderModel
import uz.codingtech.messengerdashboard.domain.models.OrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.utils.UiResult

interface OrderRepository {

    suspend fun getOrders(page: Int): UiResult<OrderPageModel>

    suspend fun postOrder(postOrderModel: PostOrderModel): UiResult<String>

    suspend fun getOrderById(id: Int): UiResult<OrderModel>

    suspend fun cancelOrder(id: Int): UiResult<Any>

    suspend fun changeActive(activeModel: ActiveModel): UiResult<ActiveModel>

}