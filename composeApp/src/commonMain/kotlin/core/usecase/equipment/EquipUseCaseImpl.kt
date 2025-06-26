package core.usecase.equipment

import core.domain.equipment.Equipment
import core.domain.equipment.EquipmentType
import core.domain.status.StatusType
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository

class EquipUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val statusDataRepository: StatusDataRepository<StatusType.Player>,
) : EquipUseCase {

    // todo 外した装備情報を返す
    // todo 装備の部位を増やす
    override suspend fun invoke(
        target: Int,
        equipment: Equipment,
    ) {
        val player = playerStatusRepository.getStatus(target)
        val parameter = statusDataRepository.getStatusData(target)

        val preEq = player.equipmentData.weapon

        val updatedParameter = parameter
            .decStatus(preEq.statusList)
            .incStatus(equipment.statusList)

        val updatedEQ = player.equipmentData.run {
            when (equipment.type) {
                EquipmentType.Weapon ->
                    copy(
                        weapon = equipment,
                    )

                EquipmentType.Shield -> {
                    copy(
                        weapon = equipment,
                    )
                }
            }
        }

        val updatedStatus = player.copy(
            equipmentData = updatedEQ,
        )

        statusDataRepository.setStatusData(
            id = target,
            statusData = updatedParameter,
        )

        playerStatusRepository.setStatus(
            id = target,
            status = updatedStatus,
        )
    }
}
