package core.usecase.updateparameter

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.status.StatusRepository
import core.usecase.updateparameter.UpdateParameterTest.Companion.HP
import core.usecase.updateparameter.UpdateParameterTest.Companion.MP
import kotlin.test.Test

class UpdateMonsterParameterUseCaseTest {
    private val status1 = TestActiveMonster.copy(
        hp = HP(
            value = HP,
            maxValue = 100,
        ),
        mp = MP(
            value = MP,
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

    private val updateParameterTest = UpdateParameterTest<MonsterStatus>(
        updateStatusUseCase,
        statusRepository,
        status2,
    )

    @Test
    fun decHP() {
        updateParameterTest.decHP()
    }

    @Test
    fun incHP() {
        updateParameterTest.incHP()
    }

    @Test
    fun decMP() {
        updateParameterTest.decMP()
    }

    @Test
    fun incMP() {
        updateParameterTest.incMP()
    }

    @Test
    fun addCondition() {
        updateParameterTest.addCondition()
    }

    @Test
    fun updateCondition() {
        updateParameterTest.setConditionList()
    }
}
