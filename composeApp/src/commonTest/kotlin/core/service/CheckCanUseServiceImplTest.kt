package core.service

import core.ModuleCore
import core.domain.item.CostType
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.param.statusParameterWithMax.MP
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckCanUseServiceImplTest : KoinTest {
    private val checkCanUseService: CheckCanUseService by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * MPが足りているので使えることを確認
     */
    @Test
    fun canUseMp() {
        val costMP = 5
        val costType = CostType.MP(costMP)
        val result = checkCanUseService.invoke(
            status = testActivePlayer.copy(
                statusData = testActivePlayer.statusData.copy(
                    mp = MP(
                        value = costMP,
                        maxValue = costMP,
                    )
                )
            ),
            costType = costType,
        )

        assertTrue {
            result
        }
    }

    /**
     * MPが足りないと使えないことを確認
     */
    @Test
    fun cannotUseMp() {
        val costMP = 5
        val costType = CostType.MP(costMP)
        val result = checkCanUseService.invoke(
            status = testActivePlayer.copy(
                statusData = testActivePlayer.statusData.copy(
                    mp = MP(
                        value = costMP - 1,
                        maxValue = costMP,
                    )
                )
            ),
            costType = costType,
        )

        assertFalse {
            result
        }
    }

    /**
     * ただの道具は使えることを確認
     */
    @Test
    fun canUseTool() {
        val costType = CostType.Consume
        val result = checkCanUseService.invoke(
            status = testActivePlayer,
            costType = costType,
        )

        assertTrue {
            result
        }
    }
}
