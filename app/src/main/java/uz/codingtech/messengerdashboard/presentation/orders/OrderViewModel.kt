package uz.codingtech.messengerdashboard.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.OrderUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.UserUseCaseModel
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderUseCaseModel: OrderUseCaseModel,
    private val userUseCaseModel: UserUseCaseModel,
    private val authUseCaseModel: AuthUseCaseModel
) : ViewModel() {

    private var currentPage = 1
    private var isLoading = false
    private var hasNext = true

    private val _state = MutableStateFlow<OrdersUiState>(OrdersUiState())
    val state: StateFlow<OrdersUiState> = _state

    fun event(ordersEvent: OrdersEvent) {
        when (ordersEvent) {
            is OrdersEvent.LoadOrders -> {
                loadOrders()
            }

            is OrdersEvent.Logout -> {
                logout()
            }
        }
    }

    init {
        getUserId()
        getBalance()
        loadOrders()
    }

    fun refreshOrders() {
        currentPage = 1
        hasNext = true
        _state.update { it.copy(successOrders = emptyList()) }
        getUserId()
        getBalance()
        loadOrders()
    }

    private fun getUserId() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    userId = authUseCaseModel.getAuthUseCase()?.user_id ?: ""
                )
            }
        }
    }

    fun loadOrders() {
        if (isLoading || !hasNext) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            _state.update { it.copy(loading = true) }
            val result = orderUseCaseModel.getOrdersUseCase(currentPage)
            when (result) {
                is UiResult.Success -> {
                    _state.update {
                        it.copy(
                            successOrders = it.successOrders + result.data.results,
                            loading = false,
                            errorMessage = null
                        )
                    }
                    hasNext = result.data.next != null
                    currentPage++
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
            isLoading = false
        }
    }

    private fun getBalance() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = userUseCaseModel.getBalanceUseCase()
            when (result) {
                is UiResult.Success -> {
                    _state.update {
                        it.copy(
                            successBalance = result.data,
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

    private fun logout() {
        viewModelScope.launch {
            authUseCaseModel.clearAuthUseCase()
        }
    }
}