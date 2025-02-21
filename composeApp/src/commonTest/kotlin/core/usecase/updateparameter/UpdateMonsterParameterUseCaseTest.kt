package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.status.StatusRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateMonsterParameterUseCaseTest {

    val hp = 50
    val mp = 30
    private val status1 = TestActiveMonster.copy(
        hp = HP(
            value = hp,
            maxValue = 100,
        ),
        mp = MP(
            value = mp,
            maxValue = 100,
        ),
    )
    private val status2 = status1.copy()

    private val statusRepository: StatusRepository<MonsterStatus> =
        object : StatusRepository<MonsterStatus> {
            var statusList: MutableList<MonsterStatus> = mutableListOf(
                status1,
                status2,
            )

            override fun getStatus(id: Int): MonsterStatus {
                return statusList[id]
            }

            override suspend fun setStatus(id: Int, status: MonsterStatus) {
                this.statusList[id] = status
            }
        }

    private val updateStatusUseCase = UpdateMonsterStatusUseCase(
        statusRepository = statusRepository,
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
                actual = statusRepository.getStatus(0).hp.value
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
                actual = statusRepository.getStatus(0).hp.value
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
                actual = statusRepository.getStatus(0).mp.value
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
                actual = statusRepository.getStatus(0).mp.value
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
                actual = statusRepository.getStatus(0).conditionList
            )

            assertEquals(
                expected = status2,
                actual = statusRepository.getStatus(1)
            )
        }
    }
}
