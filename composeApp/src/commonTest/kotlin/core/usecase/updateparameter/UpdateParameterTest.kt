package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.StatusData
import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals

// fixme テスト作る
class UpdateParameterTest(
    private val updateStatusUseCase: UpdateStatusUseCase,
    private val statusRepository: core.repository.memory.character.statusdata.StatusDataRepository,
    private val status2: StatusData,
) {
    companion object {
        const val HP = 50
        const val MP = 30
    }

    fun decHP() {
        runBlocking {
            updateStatusUseCase.decHP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = HP - 5,
                actual = statusRepository.getStatusData(0).hp.point
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    fun incHP() {
        runBlocking {
            updateStatusUseCase.incHP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = HP + 5,
                actual = statusRepository.getStatusData(0).hp.point
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    fun decMP() {
        runBlocking {
            updateStatusUseCase.decMP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = MP - 5,
                actual = statusRepository.getStatusData(0).mp.point
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

    fun incMP() {
        runBlocking {
            updateStatusUseCase.incMP(
                id = 0,
                amount = 5,
            )

            assertEquals(
                expected = MP + 5,
                actual = statusRepository.getStatusData(0).mp.point
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

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
                expected = status2,
                actual = statusRepository.getStatusData(1)
            )
        }
    }

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
                expected = status2,
                actual = statusRepository.getStatusData(1)
            )
        }
    }
}
