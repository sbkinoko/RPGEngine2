package gamescreen.battle.usecase.getmoney

import core.repository.money.MoneyRepository
import kotlinx.coroutines.flow.StateFlow
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMoneyUseCaseTestImpl : KoinTest {
    private lateinit var getMoneyUseCase: GetMoneyUseCase

    /**
     * 加算処理のテスト
     */
    @Test
    fun getMoneyTest() {
        var addCount = 0
        val moneyRepository = object : MoneyRepository {
            override val moneyStateFLow: StateFlow<Int>
                get() = throw NotImplementedError()

            override fun setMoney(money: Int) {
                throw NotImplementedError()
            }

            override fun addMoney(money: Int) {
                addCount++
            }

            override fun decMoney(money: Int) {
                throw NotImplementedError()
            }
        }

        getMoneyUseCase = GetMoneyUseCaseImpl(
            moneyRepository = moneyRepository,
        )

        getMoneyUseCase.invoke()

        assertEquals(
            expected = 1,
            actual = addCount,
        )
    }
}
