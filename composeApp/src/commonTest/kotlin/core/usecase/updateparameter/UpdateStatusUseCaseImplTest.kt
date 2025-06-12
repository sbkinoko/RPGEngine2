package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.StatusData
import core.domain.status.param.StatusParameterWithMax
import core.repository.statusdata.StatusDataRepository
import data.item.tool.ToolId
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateStatusUseCaseImplTest {

    val hp = 50
    val mp = 30
    private val status1 = testActivePlayer.run {
        copy(
            statusData = statusData.copy(
                hp = StatusParameterWithMax(
                    point = hp,
                    maxPoint = 100,
                ),
                mp = StatusParameterWithMax(
                    point = mp,
                    maxPoint = 100,
                ),
            ),
            toolList = listOf(ToolId.HEAL1, ToolId.HEAL2, ToolId.HEAL2),
        )
    }
    private val status2 = status1.copy()

    private val statusRepository: StatusDataRepository =
        object : StatusDataRepository {
            var _statusList: MutableList<StatusData> = mutableListOf(
                status1.statusData,
                status2.statusData,
            )
            override val statusDataFlow: StateFlow<List<StatusData>>
                get() = throw NotImplementedError()

            override fun getStatusData(id: Int): StatusData {
                return _statusList[id]
            }

            override fun getStatusList(): List<StatusData> {
                return _statusList
            }

            override fun setStatusList(statusList: List<StatusData>) {
                throw NotImplementedError()
            }

            override fun setStatusData(
                id: Int,
                statusData: StatusData,
            ) {
                this._statusList[id] = statusData
            }
        }

    private val updateStatusUseCase = UpdateStatusUseCaseImpl<PlayerStatus>(
        statusDataRepository = statusRepository,
    )

    @Test
    fun decHP() {
        runBlocking {
            updateStatusUseCase.decHP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = hp - 5,
                actual = statusRepository.getStatusData(0).hp.point
            )

            assertEquals(
                expected = status2.statusData,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    @Test
    fun incHP() {
        runBlocking {
            updateStatusUseCase.incHP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = hp + 5,
                actual = statusRepository.getStatusData(0).hp.point
            )

            assertEquals(
                expected = status2.statusData,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    @Test
    fun decMP() {
        runBlocking {
            updateStatusUseCase.decMP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = mp - 5,
                actual = statusRepository.getStatusData(0).mp.point
            )

            assertEquals(
                expected = status2.statusData,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    @Test
    fun incMP() {
        runBlocking {
            updateStatusUseCase.incMP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = mp + 5,
                actual = statusRepository.getStatusData(0).mp.point
            )

            assertEquals(
                expected = status2.statusData,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    @Test
    fun addCondition() {
        runBlocking {
            val condition = ConditionType.Poison()
            updateStatusUseCase.addCondition(
                id = 0,
                conditionType = condition,
            )

            assertEquals(
                expected = listOf(condition),
                actual = statusRepository.getStatusData(0).conditionList
            )

            updateStatusUseCase.addCondition(
                id = 0,
                conditionType = condition,
            )

            assertEquals(
                expected = listOf(condition, condition),
                actual = statusRepository.getStatusData(0).conditionList
            )

            assertEquals(
                expected = status2.statusData,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    @Test
    fun setConditionList() {
        runBlocking {
            val conditionList = listOf(
                ConditionType.Poison(),
                ConditionType.Paralysis(),
            )
            updateStatusUseCase.updateConditionList(
                id = 0,
                conditionList = conditionList,
            )

            assertEquals(
                expected = conditionList,
                actual = statusRepository.getStatusData(0).conditionList
            )

            assertEquals(
                expected = status2.statusData,
                actual = statusRepository.getStatusData(1)
            )
        }
    }
}
