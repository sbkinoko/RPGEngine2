package core.menu

import common.Timer
import controller.domain.ArrowCommand
import controller.domain.ControllerCallback
import controller.domain.Stick
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

interface MenuItem<T> {
    fun onClick(id: T)

    val selectedFlowState: StateFlow<T>

    var scroll: (Int) -> Unit
}

abstract class SelectableChildViewModel<T> :
    ControllerCallback,
    MenuItem<T>,
    KoinComponent {

    protected var timer: Timer = Timer(awaitTime = 200L)

    abstract var selectCore: SelectCore<T>

    override val selectedFlowState: StateFlow<T>
        get() {
            return selectCore.stateFlow
        }

    override var scroll: (Int) -> Unit = {}

    val entries: List<T>
        get() = selectCore.entries

    /**
     * selectManagerで選択可能な条件
     * 基本的には全部選択できるはず
     */
    protected open fun selectable(id: T): Boolean {
        return true
    }

    fun setSelected(id: T) {
        selectCore.select(id)
    }

    fun onClickItem(
        id: T,
    ) {
        selectCore.click(
            id,
        ) {
            goNext()
        }
    }

    override fun onClick(id: T) {
        onClickItem(id)
    }

    abstract fun goNext()

    override fun pressA() {
        goNext()
    }

    override fun pressB() {}

    override fun pressM() {}

    override fun moveStick(stick: Stick) {
        val command = stick.toCommand()

        // 放したら終了
        if (command == ArrowCommand.None)
            return

        timer.callbackIfTimePassed {
            selectCore.moveToSelectable(command) {
                selectable(it)
            }
        }
    }
}
