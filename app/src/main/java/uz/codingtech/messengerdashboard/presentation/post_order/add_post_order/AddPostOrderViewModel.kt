package uz.codingtech.messengerdashboard.presentation.post_order.add_post_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderPostModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderUseCaseModel
import uz.codingtech.messengerdashboard.utils.UiResult
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddPostOrderViewModel @Inject constructor(
    private val postOrderUseCaseModel: PostOrderUseCaseModel
) : ViewModel() {

    var navController: NavController? = null

    private fun orderUpdated() {
        navController?.previousBackStackEntry
            ?.savedStateHandle
            ?.set("order_updated", true)
    }

    private val _state = MutableStateFlow(AddPostOrderUiState())
    val state: StateFlow<AddPostOrderUiState> = _state

    fun event(addOrderEvent: AddPostOrderEvent) {
        when (addOrderEvent) {
            is AddPostOrderEvent.UploadMedia -> {
                upload(addOrderEvent.file)
            }
            is AddPostOrderEvent.PostOrderPost -> {
                postOrder(addOrderEvent.postOrderPostModel)
            }
        }
    }

    private fun postOrder(postOrderModel: PostOrderPostModel) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true,) }
            val result = postOrderUseCaseModel.postOrderPostUseCase(postOrderModel)
            when (result) {
                is UiResult.Success -> {
                    _state.update {
                        it.copy(
                            success = result.data,
                            loading = false,
                            errorMessage = null
                        )
                    }
                    orderUpdated()
                }

                is UiResult.Error -> {
                    _state.update {
                        it.copy(
                            loading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun upload(file: File) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, successUploadMedia = null,) }
            val result = postOrderUseCaseModel.uploadMediaUseCase(file)
            when (result) {
                is UiResult.Success -> {
                    _state.update {
                        it.copy(
                            successUploadMedia = result.data,
                            loading = false,
                            errorUploadMedia = null
                        )
                    }
                    orderUpdated()
                }

                is UiResult.Error -> {
                    _state.update {
                        it.copy(
                            loading = false,
                            errorUploadMedia = result.message
                        )
                    }
                }
            }
        }
    }

}