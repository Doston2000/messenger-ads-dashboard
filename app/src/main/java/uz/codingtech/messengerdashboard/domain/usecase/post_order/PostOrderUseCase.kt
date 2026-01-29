package uz.codingtech.messengerdashboard.domain.usecase.post_order

import uz.codingtech.messengerdashboard.domain.models.ActiveModelForPost
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPageModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPostModel
import uz.codingtech.messengerdashboard.domain.models.UploadMedia
import uz.codingtech.messengerdashboard.domain.repository.PostOrderRepository
import uz.codingtech.messengerdashboard.utils.UiResult
import java.io.File
import javax.inject.Inject

class UploadMediaUseCase @Inject constructor(
    private val repository: PostOrderRepository
) {
    suspend operator fun invoke(file: File): UiResult<UploadMedia> {
        return repository.postOrderMedia(file)
    }
}

class PostOrderPostUseCase @Inject constructor(
    private val repository: PostOrderRepository
) {
    suspend operator fun invoke(postOrder: PostOrderPostModel): UiResult<Any> {
        return repository.postOrder(postOrder)
    }
}

class GetPostOrdersUseCase @Inject constructor(
    private val repository: PostOrderRepository
) {
    suspend operator fun invoke(page: Int): UiResult<PostOrderPageModel> {
        return repository.getOrders(page)
    }
}

class GetPostOrderByIdUseCase @Inject constructor(
    private val repository: PostOrderRepository
) {
    suspend operator fun invoke(id: String): UiResult<PostOrderModel> {
        return repository.getOrderById(id)
    }
}

class CancelPostOrderUseCase @Inject constructor(
    private val repository: PostOrderRepository
) {
    suspend operator fun invoke(id: String): UiResult<Any> {
        return repository.cancelOrder(id)
    }
}

class ChangeActivePostOrderUseCase @Inject constructor(
    private val repository: PostOrderRepository
) {
    suspend operator fun invoke(activeModel: ActiveModelForPost): UiResult<Any> {
        return repository.changeActive(activeModel)
    }
}