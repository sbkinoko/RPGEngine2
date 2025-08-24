package core.repository.memory.money

import kotlinx.coroutines.flow.StateFlow

interface MoneyRepository {
    val moneyStateFLow: StateFlow<Int>

    fun setMoney(money: Int)

    fun addMoney(money: Int)

    fun decMoney(money: Int)

    companion object {
        const val INITIAL_MONEY = 0
    }
}
