package uz.codingtech.messengerdashboard.presentation.main_app.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.User
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authUseCaseModel: AuthUseCaseModel) :
    ViewModel() {

    sealed class AuthState {
        object Loading : AuthState()
        object Authorized : AuthState()
        object Unauthorized : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            val auth = authUseCaseModel.getAuthUseCase()

            if (auth == null || auth.accessToken.isEmpty()) {
                _authState.value = AuthState.Unauthorized
            } else {
                try {
                    authUseCaseModel.checkTokenUseCase(auth.accessToken)
                    _authState.value = AuthState.Authorized
                } catch (e: Exception) {
                    _authState.value = AuthState.Unauthorized
                }
            }
        }
    }

    fun saveAuth(authData: AuthData) {
        viewModelScope.launch {
            authUseCaseModel.saveAuthUseCase(authData)
            _authState.value = AuthState.Authorized
        }
    }

    fun logout() {
        viewModelScope.launch {
            authUseCaseModel.clearAuthUseCase()
            _authState.value = AuthState.Unauthorized
        }
    }

    fun login(user: User) {
        viewModelScope.launch {
            val authData = authUseCaseModel.loginUseCase(user)
            saveAuth(authData)
        }
    }

}