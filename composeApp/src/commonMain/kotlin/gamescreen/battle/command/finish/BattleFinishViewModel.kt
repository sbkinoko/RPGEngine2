package gamescreen.battle.command.finish

import core.usecase.changetomap.ChangeToMapUseCase
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.FinishCommand
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class BattleFinishViewModel : BattleChildViewModel() {

    private val changeToMapUseCase: ChangeToMapUseCase by inject()

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is FinishCommand
    }

    // 使わない
    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )

    override fun goNextImpl() {
        changeToMapUseCase.invoke()
    }
}
