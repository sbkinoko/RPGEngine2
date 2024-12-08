package core.repository.money

import kotlinx.coroutines.flow.StateFlow

class MoneyRepositoryImpl : MoneyRepository {
    override val moneyStateFLow: StateFlow<Int>
        get() = TODO("Not yet implemented")

    override fun setMoney(money: Int) {
        TODO("Not yet implemented")
    }
}
