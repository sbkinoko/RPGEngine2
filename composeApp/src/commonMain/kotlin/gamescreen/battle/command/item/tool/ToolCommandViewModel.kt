package gamescreen.battle.command.item.tool

import gamescreen.battle.command.item.ItemCommandViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.menu.domain.SelectManager

class ToolCommandViewModel : ItemCommandViewModel() {
    override val itemList: List<Int>
        get() = TODO("Not yet implemented")
    override val playerId: Int
        get() = TODO("Not yet implemented")
    override val actionType: ActionType
        get() = TODO("Not yet implemented")

    override var selectManager: SelectManager
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun canUse(id: Int): Boolean {
        return true
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        TODO("Not yet implemented")
    }


}
