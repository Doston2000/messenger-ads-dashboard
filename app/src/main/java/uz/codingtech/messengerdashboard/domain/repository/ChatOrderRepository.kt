package uz.codingtech.messengerdashboard.domain.repository

import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel
import uz.codingtech.messengerdashboard.utils.UiResult

interface ChatOrderRepository {

    suspend fun getOrders(page: Int): UiResult<ChatOrderPageModel>

    suspend fun postOrder(postOrderModel: PostOrderChatModel): UiResult<String>

    suspend fun getOrderById(id: Int): UiResult<ChatOrderModel>

    suspend fun cancelOrder(id: Int): UiResult<Any>

    suspend fun changeActive(activeModel: ActiveModel): UiResult<ActiveModel>

}