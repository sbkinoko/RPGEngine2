package core.menu

import controller.domain.ArrowCommand
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.StateFlow

abstract class SelectCore<T> {

    abstract var selectManager: SelectManager

    abstract val stateFlow: StateFlow<T>

    /**
     * T の要素
     */
    abstract val entries: List<T>

    abstract fun select(id: T)

    abstract fun click(
        id: T,
        goNext: () -> Unit,
    )

    fun moveToSelectable(
        command: ArrowCommand,
        selectable: (T) -> Boolean,
    ) {
        val init = selectManager.selected
        do {
            selectManager.move(command)
        } while (
        //　選択可能なもの
            selectable(stateFlow.value).not() &&
            //　一周してない
            selectManager.selected != init
        )
    }
}
