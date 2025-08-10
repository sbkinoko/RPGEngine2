package core.usecase.updateparameter

import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.repository.status.StatusRepository
import data.repository.item.tool.ToolId
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdatePlayerStatusUseCaseImplTest {

    private val status1 = testActivePlayer.run {
        copy(
            toolList = listOf(ToolId.HEAL1, ToolId.HEAL2, ToolId.HEAL2),
        )
    }
    private val status2 = status1.copy()

    private val statusRepository: StatusRepository<PlayerStatus> =
        object : StatusRepository<PlayerStatus> {
            var _statusList: MutableList<PlayerStatus> = mutableListOf(
                status1,
                status2,
            )

            override fun getStatus(id: Int): PlayerStatus {
                return _statusList[id]
            }

            override fun getStatusList(): List<PlayerStatus> {
                return _statusList
            }

            override suspend fun setStatus(
                id: Int,
                status: PlayerStatus,
            ) {
                this._statusList[id] = status
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
}
