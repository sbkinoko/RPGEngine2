package core.usecase.equipment

import core.domain.equipment.Equipment
import core.domain.status.StatusType
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository

class EquipUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val statusDataRepository: StatusDataRepository<StatusType.Player>,
) : EquipUseCase {

    override suspend fun invoke(
        target: Int,
        equipment: Equipment,
    ) {
        TODO("Not yet implemented")
    }
}
