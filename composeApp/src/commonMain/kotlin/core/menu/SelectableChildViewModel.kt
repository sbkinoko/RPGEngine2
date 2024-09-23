package core.menu

import common.Timer
import core.repository.command.CommandRepository
import org.koin.core.component.KoinComponent

abstract class SelectableChildViewModel<T> :
    SelectableWindowViewModel(),
    KoinComponent {
    protected abstract val commandRepository: CommandRepository<T>

    protected abstract val canBack: Boolean

    override var timer: Timer = Timer(awaitTime = 200L)

    private fun isBoundCommand(): Boolean {
        return isBoundedImpl(
            commandRepository.nowCommandType
        )
    }

    protected abstract fun isBoundedImpl(
        commandType: T,
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
            commandRepository.pop()
        } else {
            goNext()
        }
    }
}
