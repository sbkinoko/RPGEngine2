package gamescreen.init

import core.domain.item.BagItemData
import core.repository.bag.BagRepository
import data.repository.item.equipment.EquipmentId
import data.repository.item.tool.ToolId

class InitUseCaseImpl(
    private val equipmentBagRepository: BagRepository<EquipmentId>,
    private val toolBagRepository: BagRepository<ToolId>,
) : InitUseCase {
    override fun invoke() {

        equipmentBagRepository.setData(
            data = BagItemData(
                id = EquipmentId.Sword,
                num = 100
            )
        )
        equipmentBagRepository.setData(
            data = BagItemData(
                id = EquipmentId.Shield,
                num = 100,
            )
        )
        equipmentBagRepository.setData(
            data = BagItemData(
                id = EquipmentId.MagicSword,
                num = 100
            )
        )

        toolBagRepository.setData(
            data = BagItemData(
                id = ToolId.HEAL1,
                num = 100,
            )
        )
        toolBagRepository.setData(
            data = BagItemData(
                id = ToolId.HEAL2,
                num = 100,
            )
        )
        toolBagRepository.setData(
            data = BagItemData(
                id = ToolId.Fly,
                num = 100,
            )
        )
    }
}
