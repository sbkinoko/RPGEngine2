package gamescreen.menu.item.equipment.user

import core.EquipmentBagRepositoryName
import core.domain.item.BagItemData
import core.domain.item.equipment.EquipmentData
import core.domain.status.StatusType
import core.repository.bag.BagRepository
import core.repository.statusdata.StatusDataRepository
import data.item.equipment.EquipmentId
import data.item.equipment.EquipmentRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class EquipmentUserViewModel(
    private val statusDataRepository: StatusDataRepository<StatusType.Player>,
) : ItemUserViewModel<EquipmentId, EquipmentData>(),
    KoinComponent {
    override val itemRepository: EquipmentRepository by inject()
    private val bagRepository: BagRepository<EquipmentId> by inject(
        qualifier = EquipmentBagRepositoryName,
    )

    override val boundedScreenType: MenuType
        get() = MenuType.EQUIPMENT_LIST
    override val nextScreenType: MenuType
        get() = throw NotImplementedError()

    override val playerNum: Int
        get() = Constants.playerNum + 1

    override var selectManager: SelectManager =
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )

    // fixme 初期化タイミングを考える
    // toolGiveUseViewModelでも同じことをしている
    init {
        bagRepository.setData(
            data = BagItemData(
                id = EquipmentId.Sword,
                num = 100
            )
        )
        bagRepository.setData(
            data = BagItemData(
                id = EquipmentId.Shield,
                num = 100,
            )
        )
    }

    override fun getPlayerNameAt(id: Int): String {
        return if (id < Constants.playerNum) {
            statusDataRepository.getStatusData(id).name
        } else {
            "バッグ"
        }
    }

    override fun getPlayerItemIdListAt(id: Int): List<EquipmentId> {
        return if (id < Constants.playerNum) {
            playerStatusRepository.getStatus(id).equipmentList.list
        } else {
            bagRepository.getList().map {
                it.id
            }
        }
    }
}
