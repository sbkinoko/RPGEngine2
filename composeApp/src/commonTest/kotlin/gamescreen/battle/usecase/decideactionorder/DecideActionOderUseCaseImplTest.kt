package gamescreen.battle.usecase.decideactionorder

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import gamescreen.battle.ModuleBattle
import gamescreen.battle.domain.ActionData
import gamescreen.battle.domain.StatusWrapper
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DecideActionOderUseCaseImplTest : KoinTest {
    private val decideActionOrderUseCase: DecideActionOrderUseCase by inject()

    private val defaultData = StatusWrapper(
        id = 0,
        status = testActivePlayer,
        ActionData(),
    )

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleBattle,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun check1() {
        val list = listOf(
            defaultData.copy(
                id = 0,
                status = testActivePlayer.copy(
                    speed = 1,
                ),
            ),
            defaultData.copy(
                id = 1,
                status = testActivePlayer.copy(
                    speed = 3,
                ),
            ),
            defaultData.copy(
                id = 2,
                status = testActivePlayer.copy(
                    speed = 5,
                )
            ),
        )

        val result = decideActionOrderUseCase.invoke(list)
        assertEquals(
            expected = listOf(2, 1, 0),
            actual = result,
        )
    }

    @Test
    fun check2() {
        val list = listOf(
            defaultData.copy(
                id = 0,
                status = testActivePlayer.copy(
                    speed = 1,
                ),
            ),
            defaultData.copy(
                id = 1,
                status = testActivePlayer.copy(
                    speed = 3,
                ),
            ),
            defaultData.copy(
                id = 2,
                status = testActivePlayer.copy(
                    speed = 5,
                )
            ),
            defaultData.copy(
                id = 3,
                status = TestActiveMonster.copy(
                    speed = 4,
                )
            )
        )

        val result = decideActionOrderUseCase.invoke(list)
        assertEquals(
            expected = listOf(2, 3, 1, 0),
            actual = result,
        )
    }
}
