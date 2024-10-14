package gamescreen.battle.command.item.tool

import core.domain.Const
import core.repository.item.tool.ToolRepository
import gamescreen.battle.command.item.ItemCommandViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.ToolCommand
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class ToolCommandViewModel : ItemCommandViewModel() {
    override val itemRepository: ToolRepository by inject()

    override val itemList: List<Int>
        get() = playerRepository.getStatus(playerId).toolList
    override val playerId: Int
        get() = (commandRepository.nowCommandType as? ToolCommand)?.playerId
            ?: Const.INITIAL_PLAYER

    override val actionType: ActionType
        get() = ActionType.TOOL

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = itemList.size,
    )

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is ToolCommand
    }

    override fun getLastSelectedItemId(): Int {
        return actionRepository.getAction(
            playerId = playerId
        ).toolId
    }

    override fun canUse(id: Int): Boolean {
        return true
    }
}
