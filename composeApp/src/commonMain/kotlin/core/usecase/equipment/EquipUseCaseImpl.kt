package core.usecase.equipment

import core.domain.item.equipment.EquipmentType
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.item.equipment.EquipmentId
import data.item.equipment.EquipmentRepository

class EquipUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val statusDataRepository: StatusDataRepository,

    private val equipmentRepository: EquipmentRepository,
) : EquipUseCase {

    override suspend fun invoke(
        target: Int,
        equipmentId: EquipmentId,
    ): EquipmentId {
        val player = playerStatusRepository.getStatus(target)
        val parameter = statusDataRepository.getStatusData(target)

        val equipment = equipmentRepository.getItem(
            id = equipmentId
        )

        val preEqId: EquipmentId

        val updatedEQ = player.equipmentList.run {
            when (equipment.type) {
                EquipmentType.Weapon -> {
                    preEqId = weapon
                    copy(
                        weapon = equipmentId,
                    )
                }

                EquipmentType.Shield -> {
                    preEqId = shield
                    copy(
                        shield = equipmentId,
                    )
                }
            }
        }

        val preEq = equipmentRepository.getItem(preEqId)

        val updatedParameter = parameter
            .decStatus(preEq.statusList)
            .incStatus(equipment.statusList)

        val updatedStatus = player.copy(
            equipmentList = updatedEQ,
        )

        statusDataRepository.setStatusData(
            id = target,
            statusData = updatedParameter,
        )

        playerStatusRepository.setStatus(
            id = target,
            status = updatedStatus,
        )

        return preEqId
    }
}
