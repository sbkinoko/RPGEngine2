package gamescreen.menu.item.equipment.list

import core.EquipmentBagRepositoryName
import core.domain.AbleType
import core.domain.item.equipment.EquipmentData
import core.repository.bag.BagRepository
import data.repository.item.equipment.EquipmentId
import data.repository.item.equipment.EquipmentRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCase
import org.koin.core.component.inject
import values.Constants

class EquipmentListViewModel(
    useItemInMapUseCase: UseItemInMapUseCase,
) : ItemListViewModel<EquipmentId, EquipmentData>(
    useItemInMapUseCase = useItemInMapUseCase,
) {
    override val itemRepository: EquipmentRepository by inject()
    private val bagRepository: BagRepository<EquipmentId> by inject(
        qualifier = EquipmentBagRepositoryName,
    )

    override val selectedItem: EquipmentData
        get() = itemRepository.getItem(
            bagRepository.getItemIdAt(
                selectedFlowState.value
            )
        )

    override val boundedScreenType: MenuType
        get() = MenuType.EQUIPMENT_LIST
    override val nextScreenType: MenuType
        get() = MenuType.EQUIPMENT_TARGET

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }

    override fun goNextImpl() {
        // todo playerを選択した状態で次にすすんだらどうするかを考える

        // indexを保存
        indexRepository.index = selectCore.stateFlow.value

        //　次の画面に遷移
        menuStateRepository.push(
            nextScreenType,
        )
    }

    override fun getExplainAt(position: Int): String {
        val txt = super.getExplainAt(position)
        return if (userId < Constants.playerNum) {
            txt
        } else {
            "所持数 : " +
                    bagRepository.getList()[position].num.toString() +
                    "\n" +
                    txt
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
