package gamescreen.battle.command.item.tool

import core.domain.Const
import core.domain.item.Tool
import data.item.tool.ToolId
import data.item.tool.ToolRepository
import gamescreen.battle.command.item.ItemCommandViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.ToolCommand
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class ToolCommandViewModel : ItemCommandViewModel<ToolId, Tool>() {
    override val itemRepository: ToolRepository by inject()

    override val itemList: List<ToolId>
        get() = playerStatusRepository.getStatus(playerId).toolList
    override val playerId: Int
        get() = (commandRepository.nowBattleCommandType as? ToolCommand)?.playerId
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

    override fun getLastSelectedItemId(): ToolId {
        return actionRepository.getAction(
            playerId = playerId
        ).toolId
    }

    override fun canUse(position: Int): Boolean {
        // todo 使えない道具が出来たら変える
        return true
    }
}
