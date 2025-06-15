package gamescreen.battle.usecase.addexp

import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest.Companion.testPlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.StatusIncrease
import core.domain.status.StatusIncreaseTest.Companion.testStatusUpList
import core.domain.status.StatusType
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import core.repository.statusdata.StatusDataRepository
import data.status.AbstractStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import values.Constants
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddExpUseCaseImplFor2PlayerTest {
    private val name1 = "test1"
    private val name2 = "test2"

    private val statusRepository = object : AbstractStatusRepository() {
        override val statusUpList: List<List<StatusIncrease>>
            get() = listOf(
                testStatusUpList,
                testStatusUpList,
            )

        override val statusBaseList: List<Pair<PlayerStatus, StatusData<StatusType.Player>>>
            get() = listOf(
                Pair(
                    testPlayerStatus,
                    StatusDataTest.TestPlayerStatusActive.copy(
                        name = name1,
                    )
                ),
                Pair(
                    testPlayerStatus,
                    StatusDataTest.TestPlayerStatusActive.copy(
                        name = name2,
                    ),
                )
            )
    }

    private val statusDataRepository = object : StatusDataRepository<StatusType.Player> {
        override val statusDataFlow: StateFlow<List<StatusData<StatusType.Player>>>
            get() = throw NotImplementedError()

        private var list: MutableList<StatusData<StatusType.Player>> = mutableListOf()

        override fun getStatusData(id: Int): StatusData<StatusType.Player> {
            return list[id]
        }

        override fun getStatusList(): List<StatusData<StatusType.Player>> {
            return list
        }

        override fun setStatusList(statusList: List<StatusData<StatusType.Player>>) {
            list = statusList.toMutableList()
        }

        override fun setStatusData(
            id: Int,
            statusData: StatusData<StatusType.Player>,
        ) {
            list[id] = statusData
        }
    }

    private lateinit var playerStatusRepository: PlayerStatusRepository
    private lateinit var addExpUseCase: AddExpUseCase

    @BeforeTest
    fun beforeTest() {
        Constants.playerNum = 2

        statusDataRepository.setStatusList(
            List(Constants.playerNum) {
                statusRepository.getStatus(it, 0).second
            }
        )

        playerStatusRepository = PlayerStatusRepositoryImpl(
            statusRepository = statusRepository,
        )

        addExpUseCase = AddExpUseCaseImpl(
            playerStatusRepository = playerStatusRepository,
            statusRepository = statusRepository,
            statusDataRepository = statusDataRepository,
        )
    }

    @AfterTest
    fun afterTest() {
        Constants.playerNum = 3
    }

    @Test
    fun lvUp() {
        runBlocking {
            val result = addExpUseCase.invoke(
                exp = 1,
            )

            delay(50)


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
