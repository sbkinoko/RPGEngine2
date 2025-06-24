package core.usecase.equipment

import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.StatusType
import core.domain.status.param.EXP
import core.domain.status.param.StatusParameter
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.item.skill.SkillId
import data.item.tool.ToolId
import kotlinx.coroutines.flow.StateFlow

class EquipUseCaseImplTest {
    private val statusDataRepository = object : StatusDataRepository<StatusType.Player> {
        override val statusDataFlow: StateFlow<List<StatusData<StatusType.Player>>>
            get() = throw NotImplementedError()

        var statusData = StatusDataTest.TestPlayerStatusActive.copy(
            atk = StatusParameter(10)
        )

        override fun getStatusData(id: Int): StatusData<StatusType.Player> {
            return statusData
        }

        override fun getStatusList(): List<StatusData<StatusType.Player>> {
            throw NotImplementedError()
        }

        override fun setStatusList(statusList: List<StatusData<StatusType.Player>>) {
            throw NotImplementedError()
        }

        override fun setStatusData(
            id: Int,
            statusData: StatusData<StatusType.Player>,
        ) {
            this.statusData = statusData
        }
    }

    private val playerRepository = object : PlayerStatusRepository {
        override val playerStatusFlow: StateFlow<List<PlayerStatus>>
            get() = throw NotImplementedError()

        var status = PlayerStatus(
            skillList = emptyList(),
            toolList = emptyList(),
            exp = EXP(EXP.type1),
        )

        override fun getTool(
            playerId: Int,
            index: Int,
        ): ToolId {
            throw NotImplementedError()
        }

        override fun getSkill(
            playerId: Int,
            index: Int,
        ): SkillId {
            throw NotImplementedError()
        }

        override fun getStatus(id: Int): PlayerStatus {
            return status
        }

        override fun getStatusList(): List<PlayerStatus> {
            throw NotImplementedError()
        }

        override suspend fun setStatus(
            id: Int,
            status: PlayerStatus,
        ) {
            this.status = status
        }
    }

    lateinit var useCase: EquipUseCase
}
