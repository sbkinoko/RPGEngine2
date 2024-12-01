package core.menu

import common.Timer
import controller.domain.ArrowCommand
import controller.domain.ControllerCallback
import controller.domain.Stick
import gamescreen.menu.domain.SelectManager

abstract class SelectableWindowViewModel : ControllerCallback {
    protected abstract var selectManager: SelectManager
    protected abstract var timer: Timer

    /**
     * selectManagerで選択可能な条件
     * 基本的には全部選択できるはず
     */
    protected open fun selectable(): Boolean {
        return true
    }

    val selectedFlowState
        get() = selectManager.selectedFlowState

    fun setSelected(id: Int) {
        selectManager.selected = id
    }

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

    override fun pressB() {}
    override fun pressM() {}
}
