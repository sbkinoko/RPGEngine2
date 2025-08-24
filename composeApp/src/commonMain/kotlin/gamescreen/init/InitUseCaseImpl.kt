package gamescreen.init

import core.domain.item.BagItemData
import core.repository.bag.BagRepository
import core.repository.character.statusdata.StatusDataRepository
import core.repository.money.MoneyRepository
import data.repository.item.equipment.EquipmentId
import data.repository.item.tool.ToolId
import data.repository.status.StatusRepository
import values.Constants

class InitUseCaseImpl(
    private val equipmentBagRepository: BagRepository<EquipmentId>,
    private val toolBagRepository: BagRepository<ToolId>,
    private val statusDataRepository: StatusDataRepository,
    private val statusRepository: StatusRepository,
    private val moneyRepository: MoneyRepository,
) : InitUseCase {
    override fun invoke() {
        moneyRepository.setMoney(1000)

        statusDataRepository.setStatusList(
            List(Constants.playerNum) {
                statusRepository.getStatus(
                    id = it,
                    level = 1,
                ).second
            }
        )


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
