package uz.codingtech.messengerdashboard.presentation.dialog_order.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.ActiveModel
import uz.codingtech.messengerdashboard.domain.models.OrderUseCaseModel
import uz.codingtech.messengerdashboard.utils.UiResult

@HiltViewModel
class OrderDetailsViewModel
@Inject constructor(
    private val orderUseCaseModel: OrderUseCaseModel
) : ViewModel() {

    var navController: NavController? = null

    private fun orderUpdated() {
        navController?.previousBackStackEntry
            ?.savedStateHandle
            ?.set("order_updated", true)
    }

    private val _state = MutableStateFlow<OrderDetailUiState>(OrderDetailUiState())
    val state: StateFlow<OrderDetailUiState> = _state

    fun event(orderDetailEvent: OrderDetailEvent) {
//        if (!isInternetAvailable(serverga yuborishdan oldin view modelga tekshir)) {
//            emit(UiResult.NoInternet)
//            return@flow
//        }
        when (orderDetailEvent) {
            is OrderDetailEvent.ChangeActive -> {
                changeActive(ActiveModel(orderDetailEvent.id, orderDetailEvent.active))
            }

            is OrderDetailEvent.Cancel -> {
                cancelOrder(orderDetailEvent.id)
            }

            is OrderDetailEvent.InitOrderDetail -> {
                getOrderById(orderDetailEvent.id)
            }
        }
    }

    private fun getOrderById(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = orderUseCaseModel.getOrderByIdUseCase(id)
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

    private fun cancelOrder(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = orderUseCaseModel.cancelOrderUseCase(id)
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

    private fun changeActive(activeModel: ActiveModel) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = orderUseCaseModel.changeActiveOrderUseCase(activeModel)
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