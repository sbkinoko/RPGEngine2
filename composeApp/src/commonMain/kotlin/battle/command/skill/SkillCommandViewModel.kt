package battle.command.skill

import battle.BattleChildViewModel
import battle.domain.CommandType
import battle.domain.SkillCommand
import menu.domain.SelectManager

class SkillCommandViewModel : BattleChildViewModel() {
    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is SkillCommand
    }

    override fun goNextImpl() {

    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
