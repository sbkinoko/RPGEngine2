package common.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import common.Timer
import controller.domain.ControllerCallback
import controller.domain.StickPosition
import menu.domain.SelectManager

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

    @Composable
    fun getSelectedAsState() = selectManager.selectedFlow
        .collectAsState(selectManager.selected)

    fun setSelected(id: Int) {
        selectManager.selected = id
    }

    override fun moveStick(stickPosition: StickPosition) {
        timer.callbackIfTimePassed {
            //　スティックの方向に選択可能なものまで移動
            do {
                selectManager.move(stickPosition.toCommand())
            } while (selectable().not())
        }
    }

    override fun pressB() {}
    override fun pressM() {}
}
