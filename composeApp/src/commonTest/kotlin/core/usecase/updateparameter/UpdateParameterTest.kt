package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.Status
import core.repository.status.StatusRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals

class UpdateParameterTest<T : Status>(
    private val updateStatusUseCase: UpdateStatusUseCase<T>,
    private val statusRepository: StatusRepository<T>,
    private val status2: T,
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
                actual = statusRepository.getStatus(0).hp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).hp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).mp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).mp.value
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).conditionList
            )

            updateStatusUseCase.addCondition(
                id = 0,
                conditionType = condition,
            )

            assertEquals(
                expected = listOf(condition, condition),
                actual = statusRepository.getStatus(0).conditionList
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
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
                actual = statusRepository.getStatus(0).conditionList
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
            )
        }
    }
}
