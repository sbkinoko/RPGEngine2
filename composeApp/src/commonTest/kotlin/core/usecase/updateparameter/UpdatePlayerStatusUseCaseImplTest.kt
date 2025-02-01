package core.usecase.updateparameter

import core.domain.status.PlayerStatus
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.status.StatusRepository
import data.item.tool.ToolId
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdatePlayerStatusUseCaseImplTest {
    private val TEST_STATUS = PlayerStatus(
        name = "test1",
        hp = HP(
            maxValue = 100,
            value = 50,
        ),
        mp = MP(
            maxValue = 10,
            value = 5,
        ),
        skillList = listOf(),
        toolList = listOf(ToolId.HEAL1, ToolId.HEAL2, ToolId.HEAL2),
        exp = EXP(
            EXP.type1,
        ),
    )

    private val statusRepository: StatusRepository<PlayerStatus> =
        object : StatusRepository<PlayerStatus> {
            var status: PlayerStatus = TEST_STATUS

            override fun getStatus(id: Int): PlayerStatus {
                return status
            }

            override suspend fun setStatus(id: Int, status: PlayerStatus) {
                this.status = status
            }
        }

    @Test
    fun deleteTool() {
        val updateStatusService = UpdatePlayerStatusUseCaseImpl(
            statusRepository = statusRepository,
        )

        runBlocking {
            updateStatusService.deleteToolAt(
                playerId = 0,
                index = 2,
            )
            assertEquals(
                expected = listOf(ToolId.HEAL1, ToolId.HEAL2),
                actual = statusRepository.getStatus(0).toolList
            )
        }
    }
}
