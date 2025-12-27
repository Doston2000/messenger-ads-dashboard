package uz.codingtech.messengerdashboard.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.User
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCaseModel: AuthUseCaseModel
) : ViewModel() {

    private val _state = MutableStateFlow<LoginUiState>(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    fun event(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.Login -> {
                login(User(loginEvent.username, loginEvent.password))
            }
        }
    }

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val auth = authUseCaseModel.getAuthUseCase()
            if (auth == null || auth.access.isEmpty()) {
                _state.update {
                    it.copy(
                        success = false,
                        loading = false,
                        errorMessage = null
                    )
                }
                return@launch
            }
            val result = authUseCaseModel.checkTokenUseCase(auth.access)
            when (result) {
                is UiResult.Success -> {
                    _state.update {
                        it.copy(
                            success = true,
                            loading = false,
                            errorMessage = null
                        )
                    }
                }

                is UiResult.Error -> refreshToken(auth.refresh)
            }
        }
    }

    private fun refreshToken(refresh: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = authUseCaseModel.refreshTokenUseCase(refresh)
            when (result) {
                is UiResult.Success -> {
                    val auth = authUseCaseModel.getAuthUseCase()
                    auth?.access = result.data
                    saveAuth(auth!!)
                    _state.update {
                        it.copy(
                            success = true,
                            loading = false,
                            errorMessage = null
                        )
                    }
                }

                is UiResult.Error -> {
                    logout()
                    _state.update {
                        it.copy(
                            success = false,
                            loading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun login(user: User) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = authUseCaseModel.loginUseCase(user)
            when (result) {
                is UiResult.Success -> {
                    saveAuth(result.data)
                    _state.update {
                        it.copy(
                            success = true,
                            loading = false,
                            errorMessage = null
                        )
                    }
                }

                is UiResult.Error -> {
                    _state.update {
                        it.copy(
                            success = false,
                            loading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun saveAuth(authData: AuthData) {
        viewModelScope.launch {
            authUseCaseModel.saveAuthUseCase(authData)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authUseCaseModel.clearAuthUseCase()
        }
    }

}