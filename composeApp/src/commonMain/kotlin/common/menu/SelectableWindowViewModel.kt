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

    @Composable
    fun getSelectedAsState() = selectManager.selectedFlow
        .collectAsState(selectManager.selected)

    fun setSelected(id: Int) {
        selectManager.selected = id
    }

    override fun moveStick(stickPosition: StickPosition) {
        timer.callbackIfTimePassed {
            selectManager.move(stickPosition.toCommand())
        }
    }

    override fun pressB() {}
    override fun pressM() {}
}