package gamescreen.battle

import core.menu.IntSelectableChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.repository.commandstate.CommandStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BattleChildViewModel :
    IntSelectableChildViewModel(),
    KoinComponent {
    val commandRepository: CommandStateRepository
            by inject()

    private fun isBoundCommand(): Boolean {
        return isBoundedImpl(
            commandRepository.nowBattleCommandType
        )
    }

    protected abstract fun isBoundedImpl(
        commandType: BattleCommandType,
    ): Boolean

    override fun goNext() {
        // 別の画面状態ならなにもしない
        if (isBoundCommand().not()) {
            return
        }

        goNextImpl()
    }

    protected abstract fun goNextImpl()

    override fun pressB() {
        commandRepository.pop()
    }
}
