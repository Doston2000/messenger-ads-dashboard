package uz.codingtech.messengerdashboard.presentation.post_order.post_order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.ActiveModelForPost
import uz.codingtech.messengerdashboard.domain.models.PostOrderUseCaseModel
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

@HiltViewModel
class PostOrderDetailsViewModel @Inject constructor(
    private val orderUseCaseModel: PostOrderUseCaseModel
): ViewModel() {
    var navController: NavController? = null

    private fun orderUpdated() {
        navController?.previousBackStackEntry
            ?.savedStateHandle
            ?.set("order_updated", true)
    }

    private val _state = MutableStateFlow<PostOrderDetailUiState>(PostOrderDetailUiState())
    val state: StateFlow<PostOrderDetailUiState> = _state

    fun event(orderDetailEvent: PostOrderDetailEvent) {
        when (orderDetailEvent) {
            is PostOrderDetailEvent.ChangeActive -> {
                changeActive(ActiveModelForPost(orderDetailEvent.id, orderDetailEvent.active))
            }

            is PostOrderDetailEvent.Cancel -> {
                cancelOrder(orderDetailEvent.id)
            }

            is PostOrderDetailEvent.InitOrderDetail -> {
                getOrderById(orderDetailEvent.id)
            }
        }
    }

    private fun getOrderById(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = orderUseCaseModel.getPostOrderByIdUseCase(id)
            when (result) {
                is UiResult.Success -> {
                    _state.update {
                        it.copy(
                            success = result.data,
                            loading = false
                        )
                    }
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

    private fun cancelOrder(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = orderUseCaseModel.cancelPostOrderUseCase(id)
            when (result) {
                is UiResult.Success -> {
                    _state.update { orderDetail ->
                        orderDetail.copy(
                            success = orderDetail.success?.copy(
                                cancelled = true,
                                is_active = false
                            ),
                            loading = false
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

    private fun changeActive(activeModel: ActiveModelForPost) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = orderUseCaseModel.changeActivePostOrderUseCase(activeModel)
            when (result) {
                is UiResult.Success -> {
                    _state.update { orderDetail ->
                        orderDetail.copy(
                            success = orderDetail.success?.copy(
                                is_active = !(orderDetail.success?.is_active ?: false)
                            ),
                            loading = false
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
}