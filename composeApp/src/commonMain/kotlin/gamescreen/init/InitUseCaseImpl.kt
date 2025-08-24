package gamescreen.init

import core.domain.item.BagItemData
import core.repository.bag.BagRepository
import data.repository.item.equipment.EquipmentId

class InitUseCaseImpl(
    private val equipmentBagRepository: BagRepository<EquipmentId>,
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
    }
}
