package core.repository.money

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoneyRepositoryImpl : MoneyRepository {

    private val mutableMoneyStateFLow =
        MutableStateFlow(
            MoneyRepository.INITIAL_MONEY
        )

    override val moneyStateFLow: StateFlow<Int>
        get() = mutableMoneyStateFLow.asStateFlow()

    override fun setMoney(money: Int) {
        mutableMoneyStateFLow.value = money
    }
}
