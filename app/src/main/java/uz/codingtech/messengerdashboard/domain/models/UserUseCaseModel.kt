package uz.codingtech.messengerdashboard.domain.models

import uz.codingtech.messengerdashboard.domain.usecase.user.GetBalanceUseCase

data class UserUseCaseModel(
    val getBalanceUseCase: GetBalanceUseCase
)
