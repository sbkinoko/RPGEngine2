package core.usecase.updateparameter

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.StatusParameterWithMax
import core.repository.status.StatusRepository
import core.usecase.updateparameter.UpdateParameterTest.Companion.HP
import core.usecase.updateparameter.UpdateParameterTest.Companion.MP
import kotlin.test.Test

class UpdateMonsterParameterUseCaseTest {
    private val status1 = TestActiveMonster.run {
        copy(
            statusData = statusData.copy(
                hp = StatusParameterWithMax(
                    point = HP,
                    maxPoint = 100,
                ),
                mp = StatusParameterWithMax(
                    point = MP,
                    maxPoint = 100,
                ),
            ),
        )
    }
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

            override fun getStatusList(): List<MonsterStatus> {
                return statusList
            }

            override suspend fun setStatus(
                id: Int,
                status: MonsterStatus,
            ) {
                this.statusList[id] = status
            }
        }

    private val updateStatusUseCase = UpdateMonsterStatusUseCase(
        statusRepository = statusRepository,
    )

    private val updateParameterTest = UpdateParameterTest(
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
