package uz.codingtech.messengerdashboard.domain.usecase.auth

import retrofit2.Response
import uz.codingtech.messengerdashboard.domain.models.AccessToken
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.RefreshToken
import uz.codingtech.messengerdashboard.domain.models.Status
import uz.codingtech.messengerdashboard.domain.models.Token
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
    suspend operator fun invoke(token: Token): Response<Status>? {
        return repository.checkToken(token)
    }
}

class RefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(refresh: RefreshToken): Response<AccessToken>? {
        return repository.refreshToken(refresh)
    }
}

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User): Response<AuthData>? {
        return repository.login(user)
    }
}