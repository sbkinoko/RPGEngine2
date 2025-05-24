package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.param.statusParameterWithMax.HP
import core.domain.status.param.statusParameterWithMax.MP
import core.repository.status.StatusRepository
import data.item.tool.ToolId
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdatePlayerStatusUseCaseImplTest {

    val hp = 50
    val mp = 30
    private val status1 = testActivePlayer.run {
        copy(
            statusData = statusData.copy(
                hp = HP(
                    value = hp,
                    maxValue = 100,
                ),
                mp = MP(
                    value = mp,
                    maxValue = 100,
                ),
            ),
            toolList = listOf(ToolId.HEAL1, ToolId.HEAL2, ToolId.HEAL2),
        )
    }
    private val status2 = status1.copy()

    private val statusRepository: StatusRepository<PlayerStatus> =
        object : StatusRepository<PlayerStatus> {
            var statusList: MutableList<PlayerStatus> = mutableListOf(
                status1,
                status2,
            )

            override fun getStatus(id: Int): PlayerStatus {
                return statusList[id]
            }

            override suspend fun setStatus(id: Int, status: PlayerStatus) {
                this.statusList[id] = status
            }
        }

    private val updateStatusUseCase = UpdatePlayerStatusUseCaseImpl(
        statusRepository = statusRepository,
    )

    @Test
    fun deleteTool() {
        runBlocking {
            updateStatusUseCase.deleteToolAt(
                playerId = 0,
                index = 2,
            )
            assertEquals(
                expected = listOf(ToolId.HEAL1, ToolId.HEAL2),
                actual = statusRepository.getStatus(0).toolList
            )
        }
    }


    @Test
    fun decHP() {
        runBlocking {
            updateStatusUseCase.decHP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = hp - 5,
                actual = statusRepository.getStatus(0).statusData.hp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).statusData.hp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).statusData.mp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).statusData.mp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).statusData.conditionList
            )

            updateStatusUseCase.addCondition(
                id = 0,
                conditionType = condition,
            )

            assertEquals(
                expected = listOf(condition, condition),
                actual = statusRepository.getStatus(0).statusData.conditionList
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).statusData.conditionList
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
            )
        }
    }
}
