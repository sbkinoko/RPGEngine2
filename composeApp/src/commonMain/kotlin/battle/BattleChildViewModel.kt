package battle

import battle.domain.CommandType
import battle.repository.commandstate.CommandStateRepository
import common.Timer
import common.menu.CommonMenuViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BattleChildViewModel : CommonMenuViewModel(), KoinComponent {
    abstract val boundCommand: CommandType
    protected val commandStateRepository: CommandStateRepository by inject()

    override var timer: Timer = Timer(awaitTime = 200L)

    fun onClickItem(
        id: Int,
        callback: () -> Unit,
    ) {
        // 選択されていたらコールバック
        if (selectManager.selected == id) {
            callback()
            return
        }

        //　今クリックしたやつを選択
        selectManager.selected = id
    }

    fun goNext() {
        // 別の画面状態ならなにもしない
        if (commandStateRepository.nowCommandType != boundCommand) {
            return
        }

        goNextImpl()

    }

    protected abstract fun goNextImpl()

    override var pressA: () -> Unit = {
        goNext()
    }
}
