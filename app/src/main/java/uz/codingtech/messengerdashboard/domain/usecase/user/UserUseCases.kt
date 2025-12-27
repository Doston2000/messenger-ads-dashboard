package uz.codingtech.messengerdashboard.domain.usecase.user

import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.repository.UserRepository
import uz.codingtech.messengerdashboard.utils.UiResult
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): UiResult<Balance> {
        return repository.getBalance()
    }
}