package core.menu

import common.Timer
import controller.domain.ArrowCommand
import controller.domain.ControllerCallback
import controller.domain.Stick
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.KoinComponent

abstract class SelectableChildViewModel :
    ControllerCallback,
    KoinComponent {
    var timer: Timer = Timer(awaitTime = 200L)

    protected abstract var selectManager: SelectManager

    val selectedFlowState
        get() = selectManager.selectedFlowState

    /**
     * selectManagerで選択可能な条件
     * 基本的には全部選択できるはず
     */
    protected open fun selectable(): Boolean {
        return true
    }
    
    fun setSelected(id: Int) {
        selectManager.selected = id
    }

    fun onClickItem(
        id: Int,
    ) {
        // 選択されていたらコールバック
        if (selectManager.selected == id) {
            goNext()
            return
        }

        //　今クリックしたやつを選択
        selectManager.selected = id
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
            moveToSelectable(command)
        }
    }

    /**
     * 選択可能なものまで移動
     * 無理ならそのまま
     */
    private fun moveToSelectable(command: ArrowCommand) {
        val init = selectManager.selected
        do {
            selectManager.move(command)
        } while (
        //　選択可能なもの
            selectable().not() &&
            //　一周してない
            selectManager.selected != init
        )
    }
}
