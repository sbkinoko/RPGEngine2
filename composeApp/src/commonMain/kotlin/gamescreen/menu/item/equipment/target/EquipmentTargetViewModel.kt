package gamescreen.menu.item.equipment.target

import core.EquipmentBagRepositoryName
import core.PlayerStatusRepositoryName
import core.domain.status.StatusType
import core.repository.bag.BagRepository
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import core.usecase.equipment.EquipUseCase
import data.item.equipment.EquipmentId
import data.item.equipment.EquipmentRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

class EquipmentTargetViewModel(
    private val equipmentUseCase: EquipUseCase,
) : MenuChildViewModel() {
    private val userRepository: UserRepository by inject()
    private val targetRepository: TargetRepository by inject()
    val playerStatusRepository: PlayerStatusRepository by inject()
    val statusDataRepository: StatusDataRepository<StatusType.Player> by inject(
        qualifier = PlayerStatusRepositoryName
    )

    private val bagRepository: BagRepository<EquipmentId> by inject(
        qualifier = EquipmentBagRepositoryName,
    )

    private val choiceRepository: ChoiceRepository by inject()

    val itemRepository: EquipmentRepository by inject()

    val textRepository: TextRepository by inject()

    val user: Int
        get() = userRepository.userId

    val itemId: EquipmentId
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

    val explain: String
        get() = itemRepository.getItem(itemId).explain

    val boundedMenuType: MenuType
        get() = MenuType.EQUIPMENT_TARGET

    private val indexRepository: IndexRepository by inject()

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedMenuType
    }

    override fun goNextImpl() {
        targetRepository.target = selectManager.selected
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

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )

    override fun selectable(): Boolean {
        return canSelect(selectManager.selected)
    }

    fun canSelect(target: Int): Boolean {
        return true
    }

    private fun selectYes() {
        CoroutineScope(Dispatchers.Default).launch {
            equipmentUseCase.invoke(
                target = targetRepository.target,
                equipmentId = itemId,
            )

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
