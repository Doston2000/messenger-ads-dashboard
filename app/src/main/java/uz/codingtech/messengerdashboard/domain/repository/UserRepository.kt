package uz.codingtech.messengerdashboard.domain.repository

import uz.codingtech.messengerdashboard.domain.models.Balance
import uz.codingtech.messengerdashboard.utils.UiResult

interface UserRepository {

    suspend fun getBalance(): UiResult<Balance>

}