package gamescreen.battle.usecase.addexp

import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest
import core.domain.status.PlayerStatusTest.Companion.testPlayerStatus
import core.domain.status.StatusIncrease
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_MP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_MP
import core.domain.status.StatusIncreaseTest.Companion.testStatusUpList
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import data.status.AbstractStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import values.Constants
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddExpUseCaseImplTest {

    private val statusRepository = object : AbstractStatusRepository() {
        override val statusUpList: List<List<StatusIncrease>>
            get() = listOf(
                testStatusUpList,
            )
        override val statusBaseList: List<PlayerStatus>
            get() = listOf(
                testPlayerStatus
            )
    }

    private lateinit var playerStatusRepository: PlayerStatusRepository
    private lateinit var addExpUseCase: AddExpUseCase

    @BeforeTest
    fun beforeTest() {
        Constants.playerNum = 1


        playerStatusRepository = PlayerStatusRepositoryImpl(
            statusRepository = statusRepository,
        )

        addExpUseCase = AddExpUseCaseImpl(
            playerStatusRepository = playerStatusRepository,
            statusRepository = statusRepository,
        )
    }

    @AfterTest
    fun afterTest() {
        Constants.playerNum = 3
    }

    @Test
    fun notLvUp() {

        runBlocking {
            val result = addExpUseCase.invoke(
                exp = 0,
            )

            assertTrue {
                result.isEmpty()
            }
        }
    }

    @Test
    fun lvUp() {
        runBlocking {
            val result = addExpUseCase(
                exp = 1,
            )

            delay(50)

            assertTrue {
                result.isNotEmpty()
            }

            assertEquals(
                expected = PlayerStatusTest.NAME,
                actual = result.first()
            )

            playerStatusRepository.getStatus(0).apply {
                assertEquals(
                    expected = TEST_LV1_HP,
                    actual = hp.value,
                )

                assertEquals(
                    expected = TEST_LV1_HP + TEST_LV2_HP,
                    actual = hp.maxValue,
                )

                assertEquals(
                    expected = TEST_LV1_MP,
                    actual = mp.value,
                )

                assertEquals(
                    expected = TEST_LV1_MP + TEST_LV2_MP,
                    actual = mp.maxValue,
                )
            }
        }
    }
}
