package gamescreen.battle.usecase.getexp

import gamescreen.battle.usecase.getExp.GetExpUseCase
import gamescreen.battle.usecase.getExp.GetExpUseCaseImpl
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetExpUseCaseTestImpl : KoinTest {
    private lateinit var getExpUseCase: GetExpUseCase

    /**
     * 入手経験値のテスト
     */
    @Test
    fun getExpTest() {
        getExpUseCase = GetExpUseCaseImpl()

        val exp = getExpUseCase.invoke()

        assertEquals(
            expected = 10,
            actual = exp,
        )
    }
}
