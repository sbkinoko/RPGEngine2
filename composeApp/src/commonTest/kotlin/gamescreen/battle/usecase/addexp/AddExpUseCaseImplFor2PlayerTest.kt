package gamescreen.battle.usecase.addexp

import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest.Companion.testPlayerStatus
import core.domain.status.StatusIncrease
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

class AddExpUseCaseImplFor2PlayerTest {
    private val name1 = "test1"
    private val name2 = "test2"

    private val statusRepository = object : AbstractStatusRepository() {
        override val statusUpList: List<List<StatusIncrease>>
            get() = listOf(
                testStatusUpList,
                testStatusUpList,
            )
        override val statusBaseList: List<PlayerStatus>
            get() = listOf(
                testPlayerStatus.copy(
                    name = name1,
                ),
                testPlayerStatus.copy(
                    name = name2,
                )
            )
    }

    private lateinit var playerStatusRepository: PlayerStatusRepository
    private lateinit var addExpUseCase: AddExpUseCase

    @BeforeTest
    fun beforeTest() {
        Constants.playerNum = 2

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
                expected = name1,
                actual = result[0]
            )

            assertEquals(
                expected = name2,
                actual = result[1]
            )
        }
    }
}
