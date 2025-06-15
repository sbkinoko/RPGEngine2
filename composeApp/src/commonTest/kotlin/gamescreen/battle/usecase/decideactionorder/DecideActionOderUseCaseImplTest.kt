package gamescreen.battle.usecase.decideactionorder

import core.domain.status.StatusDataTest
import core.domain.status.StatusType
import core.domain.status.param.StatusParameter
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
        status = StatusDataTest.TestPlayerStatusInActive,
        actionData = ActionData(),
        statusType = StatusType.Player,
        newId = 0,
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
                newId = 1,
                status = StatusDataTest.TestPlayerStatusInActive.copy(
                    speed = StatusParameter(1),
                ),
            ),
            defaultData.copy(
                newId = 2,
                status = StatusDataTest.TestPlayerStatusInActive.copy(
                    speed = StatusParameter(3),
                ),
            ),
            defaultData.copy(
                newId = 3,
                status = StatusDataTest.TestPlayerStatusInActive.copy(
                    speed = StatusParameter(5),
                ),
            )
        )

        val result = decideActionOrderUseCase.invoke(list)

        assertEquals(
            expected = 3,
            actual = result[0].newId,
        )
        assertEquals(
            expected = 2,
            actual = result[1].newId,
        )
        assertEquals(
            expected = 1,
            actual = result[2].newId,
        )
    }

    @Test
    fun check2() {
        val list = listOf(
            defaultData.copy(
                newId = 0,
                status = StatusDataTest.TestPlayerStatusInActive.copy(
                    speed = StatusParameter(1),
                ),
            ),
            defaultData.copy(
                newId = 1,
                status = StatusDataTest.TestPlayerStatusInActive.copy(
                    speed = StatusParameter(3),
                ),
            ),
            defaultData.copy(
                newId = 2,
                status = StatusDataTest.TestPlayerStatusInActive.copy(
                    speed = StatusParameter(5),
                ),
            ),
            defaultData.copy(
                newId = 3,
                status = StatusDataTest.TestPlayerStatusInActive.copy(
                    speed = StatusParameter(4),
                ),
            )
        )

        val result = decideActionOrderUseCase.invoke(list)

        val expect = listOf(2, 3, 1, 0)
        for (id: Int in expect.indices) {
            assertEquals(
                expected = expect[id],
                actual = result[id].newId,
            )
        }
    }
}
