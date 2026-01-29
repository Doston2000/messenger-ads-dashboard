package uz.codingtech.messengerdashboard.presentation.post_order.post_orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.PostOrderUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.UserUseCaseModel
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

@HiltViewModel
class PostOrdersViewModel @Inject constructor(
    private val userUseCaseModel: UserUseCaseModel,
    private val postOrderUseCaseModel: PostOrderUseCaseModel
): ViewModel() {

    private var currentPage = 1
    private var isLoading = false
    private var hasNext = true

    private val _state = MutableStateFlow(PostOrdersUiState())
    val state: StateFlow<PostOrdersUiState> = _state

    fun event(ordersEvent: PostOrdersEvent) {
        when (ordersEvent) {
            is PostOrdersEvent.LoadOrders -> {
                loadOrders()
            }
        }
    }

    init {
        getBalance()
        loadOrders()
    }

    fun refreshOrders() {
        currentPage = 1
        hasNext = true
        _state.update { it.copy(successOrders = emptyList()) }
        getBalance()
        loadOrders()
    }

    fun loadOrders() {
        if (isLoading || !hasNext) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            _state.update { it.copy(loading = true) }
            val result = postOrderUseCaseModel.getPostOrdersUseCase(currentPage)
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

}