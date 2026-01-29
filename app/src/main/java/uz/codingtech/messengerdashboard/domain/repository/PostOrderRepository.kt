package uz.codingtech.messengerdashboard.domain.repository

import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.ActiveModelForPost
import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPostModel
import uz.codingtech.messengerdashboard.domain.models.UploadMedia
import uz.codingtech.messengerdashboard.utils.UiResult
import java.io.File

interface PostOrderRepository {
    suspend fun getOrders(page: Int): UiResult<PostOrderPageModel>

    suspend fun postOrderMedia(file: File): UiResult<UploadMedia>

    suspend fun postOrder(postOrderModel: PostOrderPostModel): UiResult<Any>

    suspend fun getOrderById(id: String): UiResult<PostOrderModel>

    suspend fun cancelOrder(id: String): UiResult<Any>

    suspend fun changeActive(activeModel: ActiveModelForPost): UiResult<Any>

}