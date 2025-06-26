package core.usecase.equipment

import core.domain.item.equipment.EquipmentType
import core.domain.status.StatusType
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.item.equipment.EquipmentId
import data.item.equipment.EquipmentRepository

class EquipUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val statusDataRepository: StatusDataRepository<StatusType.Player>,

    private val equipmentRepository: EquipmentRepository,
) : EquipUseCase {

    // todo 外した装備情報を返す
    // todo 装備の部位を増やす
    override suspend fun invoke(
        target: Int,
        equipmentId: EquipmentId,
    ) {
        val player = playerStatusRepository.getStatus(target)
        val parameter = statusDataRepository.getStatusData(target)

        val equipment = equipmentRepository.getItem(
            id = equipmentId
        )

        val preEqid = player.equipmentList.weapon
        val preEq = equipmentRepository.getItem(preEqid)

        val updatedParameter = parameter
            .decStatus(preEq.statusList)
            .incStatus(equipment.statusList)

        val updatedEQ = player.equipmentList.run {
            when (equipment.type) {
                EquipmentType.Weapon ->
                    copy(
                        weapon = equipmentId,
                    )

                EquipmentType.Shield -> {
                    copy(
                        weapon = equipmentId,
                    )
                }
            }
        }

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
    }
}
