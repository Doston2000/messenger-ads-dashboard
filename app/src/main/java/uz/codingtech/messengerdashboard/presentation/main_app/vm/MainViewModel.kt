package uz.codingtech.messengerdashboard.presentation.main_app.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.Token
import uz.codingtech.messengerdashboard.domain.models.User
import uz.codingtech.messengerdashboard.domain.models.UserUseCaseModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCaseModel: AuthUseCaseModel,
    private val userUseCaseModel: UserUseCaseModel
) :
    ViewModel() {

    sealed class AuthState {
        object Loading : AuthState()
        object Authorized : AuthState()
        object Unauthorized : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    private val _userData = MutableStateFlow<AuthData?>(null)
    val userData: StateFlow<AuthData?> = _userData

    private val _userBalance = MutableStateFlow<Balance?>(null)
    val userBalance: StateFlow<Balance?> = _userBalance

    fun checkLogin() {
        viewModelScope.launch {
            val auth = authUseCaseModel.getAuthUseCase()

            if (auth == null || auth.access.isEmpty()) {
                _authState.value = AuthState.Unauthorized
            } else {
                try {
                    val result = authUseCaseModel.checkTokenUseCase(Token(auth.access))
                    if (result != null && result.isSuccessful) {
                        _authState.value = AuthState.Authorized
                    } else {
                        refreshToken(auth.refresh)
                    }
                } catch (e: Exception) {
                    logout()
                }
            }
        }
    }

    fun refreshToken(refresh: String) {
        viewModelScope.launch {
            val result = authUseCaseModel.refreshTokenUseCase(RefreshToken(refresh))
            if (result != null && result.isSuccessful) {
                val auth = authUseCaseModel.getAuthUseCase()
                auth?.access = result.body()?.access ?: ""
                saveAuth(auth!!)
            } else {
                logout()
            }
        }

    }

    fun saveAuth(authData: AuthData) {
        viewModelScope.launch {
            authUseCaseModel.saveAuthUseCase(authData)
            _authState.value = AuthState.Authorized
            _userData.value = authData
        }
    }

    fun logout() {
        viewModelScope.launch {
            authUseCaseModel.clearAuthUseCase()
            _authState.value = AuthState.Unauthorized
            _userData.value = null
        }
    }

    fun login(user: User, call: (String?) -> Unit) {
        viewModelScope.launch {
            val authData = authUseCaseModel.loginUseCase(user)
            if (authData != null && authData.isSuccessful) {
                saveAuth(authData.body()!!)
                call(null)
            } else {
                call(authData?.message())
            }
        }
    }

    fun getBalance() {
        viewModelScope.launch {
            val balance = userUseCaseModel.getBalanceUseCase()
            if (balance != null && balance.isSuccessful){
                _userBalance.value = balance.body()
            }
        }
    }

}