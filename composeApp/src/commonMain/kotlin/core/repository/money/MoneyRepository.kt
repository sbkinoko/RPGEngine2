package core.repository.money

import kotlinx.coroutines.flow.StateFlow

interface MoneyRepository {
    val moneyStateFLow: StateFlow<Int>

    fun setMoney(money: Int)
}
