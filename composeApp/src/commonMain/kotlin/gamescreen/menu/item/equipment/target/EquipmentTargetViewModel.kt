package gamescreen.menu.item.equipment.target

import common.DefaultScope
import core.EquipmentBagRepositoryName
import core.PlayerStatusRepositoryName
import core.domain.item.equipment.EquipmentData
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.bag.BagRepository
import core.repository.character.player.PlayerCharacterRepository
import core.repository.character.statusdata.StatusDataRepository
import core.usecase.equipment.EquipUseCase
import data.repository.item.equipment.EquipmentId
import data.repository.item.equipment.EquipmentRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.target.TargetViewModel
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menu.usecase.bag.dectool.DecItemUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

class EquipmentTargetViewModel(
    private val equipmentUseCase: EquipUseCase,

    private val decEquipmentUseCase: DecItemUseCase<EquipmentId>,
    private val addEquipmentUseCase: AddToolUseCase<EquipmentId>,
) : TargetViewModel<EquipmentId, EquipmentData>() {
    private val targetRepository: TargetRepository by inject()
    val playerStatusRepository: PlayerCharacterRepository by inject()
    val statusDataRepository: StatusDataRepository by inject(
        qualifier = PlayerStatusRepositoryName
    )

    private val bagRepository: BagRepository<EquipmentId> by inject(
        qualifier = EquipmentBagRepositoryName,
    )

    private val choiceRepository: ChoiceRepository by inject()

    override val itemRepository: EquipmentRepository by inject()

    val textRepository: TextRepository by inject()

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )
    )

    override val itemId: EquipmentId
        get() {
            val index = indexRepository.index

            return if (user < playerNum) {
                playerStatusRepository.getStatus(
                    user
                ).equipmentList.list[index]
            } else {
                bagRepository.getItemIdAt(index)
            }
        }

    private val boundedMenuType: MenuType
        get() = MenuType.EQUIPMENT_TARGET

    private val indexRepository: IndexRepository by inject()

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedMenuType
    }

    override fun goNextImpl() {
        targetRepository.target = selectCore.stateFlow.value
        choiceRepository.push(
            listOf(
                Choice(
                    text = "つける",
                    callBack = {
                        selectYes()
                    }
                ),
                Choice(
                    text = "やめる",
                    callBack = { }
                ),
            ))
    }


    override fun selectable(id: Int): Boolean {
        return canSelect(id)
    }

    override fun canSelect(target: Int): Boolean {
        return true
    }

    private fun selectYes() {
        DefaultScope.launch {

            val preId = equipmentUseCase.invoke(
                target = targetRepository.target,
                equipmentId = itemId,
            )

            if (userRepository.userId == playerNum) {
                decEquipmentUseCase.invoke(
                    itemId = itemId,
                    itemNum = 1,
                )

                addEquipmentUseCase.invoke(
                    toolId = preId,
                    toolNum = 1,
                )
            }

            // textを表示
            textRepository.push(
                TextBoxData(
                    text = "装備をつけました",
                    callBack = { commandRepository.pop() }
                )
            )
        }
    }
}
