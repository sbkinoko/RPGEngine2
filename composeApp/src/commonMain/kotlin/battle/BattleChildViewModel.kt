package battle

import battle.domain.CommandType
import battle.repository.commandstate.CommandStateRepository
import common.Timer
import common.menu.CommonMenuViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BattleChildViewModel :
    CommonMenuViewModel(),
    KoinComponent {
    protected val commandStateRepository: CommandStateRepository by inject()

    protected abstract val canBack: Boolean

    override var timer: Timer = Timer(awaitTime = 200L)

    private fun isBoundCommand(): Boolean {
        return isBoundedImpl(
            commandStateRepository.nowCommandType
        )
    }

    protected abstract fun isBoundedImpl(
        commandType: CommandType,
    ): Boolean

    fun onClickItem(
        id: Int,
    ) {
        // 選択されていたらコールバック
        if (selectManager.selected == id) {
            goNextImpl()
            return
        }

        //　今クリックしたやつを選択
        selectManager.selected = id
    }

    private fun goNext() {
        // 別の画面状態ならなにもしない
        if (isBoundCommand().not()) {
            return
        }

        goNextImpl()
    }

    protected abstract fun goNextImpl()

    override fun pressA() {
        goNext()
    }

    override fun pressB() {
        if (canBack) {
            commandStateRepository.pop()
        } else {
            goNext()
        }
    }
}
