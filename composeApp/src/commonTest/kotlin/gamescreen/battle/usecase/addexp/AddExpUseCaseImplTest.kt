package gamescreen.battle.usecase.addexp

import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest.Companion.testPlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusIncrease
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_MP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_MP
import core.domain.status.StatusIncreaseTest.Companion.testStatusUpList
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import core.repository.statusdata.StatusDataRepository
import data.repository.status.AbstractStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import values.Constants
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddExpUseCaseImplTest {
    val name = "test"

    private val statusRepository = (object : AbstractStatusRepository() {
        override val statusUpList: List<List<StatusIncrease>>
            get() = listOf(
                testStatusUpList,
            )
        override val statusBaseList: List<Pair<PlayerStatus, StatusData>>
            get() = listOf(
                Pair(
                    testPlayerStatus,
                    StatusData(name = name),
                )
            )
    })

    private val statusDataRepository = object : StatusDataRepository {
        override val statusDataFlow: StateFlow<List<StatusData>>
            get() = throw NotImplementedError()

        private var list: MutableList<StatusData> = mutableListOf()

        override fun getStatusData(id: Int): StatusData {
            return list[id]
        }

        override fun getStatusList(): List<StatusData> {
            return list
        }

        override fun setStatusList(statusList: List<StatusData>) {
            list = statusList.toMutableList()
        }

        override fun setStatusData(
            id: Int,
            statusData: StatusData,
        ) {
            list[id] = statusData
        }
    }

    private lateinit var playerStatusRepository: PlayerStatusRepository
    private lateinit var addExpUseCase: AddExpUseCase

    @BeforeTest
    fun beforeTest() {
        Constants.playerNum = 1

        statusDataRepository.setStatusList(
            List(Constants.playerNum) {
                statusRepository.getStatus(it, 1).second
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
                expected = name,
                actual = result.first()
            )

            statusDataRepository.getStatusData(0).apply {
                assertEquals(
                    expected = TEST_LV1_HP,
                    actual = hp.point,
                )

                assertEquals(
                    expected = TEST_LV1_HP + TEST_LV2_HP,
                    actual = hp.maxPoint,
                )

                assertEquals(
                    expected = TEST_LV1_MP,
                    actual = mp.point,
                )

                assertEquals(
                    expected = TEST_LV1_MP + TEST_LV2_MP,
                    actual = mp.maxPoint,
                )
            }
        }
    }
}
