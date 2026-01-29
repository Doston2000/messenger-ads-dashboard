package uz.codingtech.messengerdashboard.presentation.chat_order.add_chat_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.ChatOrderUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

@HiltViewModel
class AddChatOrderViewModel@Inject constructor(
    private val orderUseCaseModel: ChatOrderUseCaseModel
): ViewModel() {

    var navController: NavController? = null

    private fun orderUpdated() {
        navController?.previousBackStackEntry
            ?.savedStateHandle
            ?.set("order_updated", true)
    }

    private val _state = MutableStateFlow(AddChatOrderUiState())
    val state: StateFlow<AddChatOrderUiState> = _state

    fun event(addOrderEvent: AddChatOrderEvent) {
        when(addOrderEvent){
            is AddChatOrderEvent.PostOrder ->{
                postOrder(addOrderEvent.postOrderModel)
            }
        }
    }

    private fun postOrder(postOrderModel: PostOrderChatModel) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true,) }
            val result = orderUseCaseModel.postOrderUseCase(postOrderModel)
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

}