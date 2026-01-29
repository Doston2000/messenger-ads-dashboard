package uz.codingtech.messengerdashboard.presentation.post_order.add_post_order

import uz.codingtech.messengerdashboard.domain.models.PostOrderPostModel
import java.io.File

sealed class AddPostOrderEvent {
    data class UploadMedia(val file: File) : AddPostOrderEvent()
    data class PostOrderPost(val postOrderPostModel: PostOrderPostModel) : AddPostOrderEvent()
}