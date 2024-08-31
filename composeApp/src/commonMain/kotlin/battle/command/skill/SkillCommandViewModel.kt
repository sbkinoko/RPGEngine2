package battle.command.skill

import battle.BattleChildViewModel
import battle.domain.CommandType
import menu.domain.SelectManager

class SkillCommandViewModel : BattleChildViewModel() {
    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        TODO("Not yet implemented")
    }

    override fun goNextImpl() {
        TODO("Not yet implemented")
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
