package uz.codingtech.messengerdashboard.domain.usecase.auth

import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.User
import uz.codingtech.messengerdashboard.domain.repository.AuthRepository
import javax.inject.Inject

class SaveAuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(authData: AuthData) {
        repository.saveAuth(authData)
    }
}

class GetAuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AuthData? {
        return repository.getAuth()
    }
}

class ClearAuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.clearAuth()
    }
}

class CheckTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(token: String) {
        repository.checkToken(token)
    }
}

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User): AuthData {
        return repository.login(user)
    }
}