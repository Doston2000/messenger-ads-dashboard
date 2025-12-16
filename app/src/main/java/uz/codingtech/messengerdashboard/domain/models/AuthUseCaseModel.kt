package uz.codingtech.messengerdashboard.domain.models

import uz.codingtech.messengerdashboard.domain.usecase.auth.CheckTokenUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.ClearAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.GetAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.LoginUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.RefreshTokenUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.SaveAuthUseCase

data class AuthUseCaseModel(
    val loginUseCase: LoginUseCase,
    val checkTokenUseCase: CheckTokenUseCase,
    val refreshTokenUseCase: RefreshTokenUseCase,
    val getAuthUseCase: GetAuthUseCase,
    val saveAuthUseCase: SaveAuthUseCase,
    val clearAuthUseCase: ClearAuthUseCase
)
