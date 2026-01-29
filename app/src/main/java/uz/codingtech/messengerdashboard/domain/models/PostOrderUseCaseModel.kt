package uz.codingtech.messengerdashboard.domain.models

import uz.codingtech.messengerdashboard.domain.usecase.post_order.CancelPostOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.ChangeActivePostOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.GetPostOrderByIdUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.GetPostOrdersUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.PostOrderPostUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.UploadMediaUseCase

data class PostOrderUseCaseModel(
    val uploadMediaUseCase: UploadMediaUseCase,
    val postOrderPostUseCase: PostOrderPostUseCase,
    val getPostOrdersUseCase: GetPostOrdersUseCase,
    val getPostOrderByIdUseCase: GetPostOrderByIdUseCase,
    val cancelPostOrderUseCase: CancelPostOrderUseCase,
    val changeActivePostOrderUseCase: ChangeActivePostOrderUseCase
)
