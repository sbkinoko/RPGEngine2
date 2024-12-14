package gamescreen.battle.usecase.getmoney

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
        getMoneyUseCase = GetMoneyUseCaseImpl()

        val money = getMoneyUseCase.invoke()

        assertEquals(
            expected = 10,
            actual = money,
        )
    }
}
