package uz.codingtech.messengerdashboard.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.UserUseCaseModel
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

@HiltViewModel
class MenuViewMode @Inject constructor(
    private val authUseCaseModel: AuthUseCaseModel,
    private val userUseCaseModel: UserUseCaseModel
    ) : ViewModel() {

    private val _state = MutableStateFlow(MenuUiState())
    val state: StateFlow<MenuUiState> = _state

    fun event(menuEvent: MenuEvent) {
        when (menuEvent) {
            is MenuEvent.Logout -> {
                logout()
            }
        }
    }

    init {
        getUserId()
        getBalance()
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