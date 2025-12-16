package uz.codingtech.messengerdashboard.domain.usecase.user

import retrofit2.Response
import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.domain.repository.UserRepository
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Response<Balance>? {
        return repository.getBalance()
    }
}