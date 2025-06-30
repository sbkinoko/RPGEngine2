package core.menu

import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.StateFlow

class SelectCoreInt(
    override var selectManager: SelectManager,
) : SelectCore<Int>() {
    override val stateFlow: StateFlow<Int>
        get() = selectManager.selectedFlowState

    override fun select(id: Int) {
        selectManager.selected = id
    }

    override val entries: List<Int>
        get() = TODO("Not yet implemented")

    fun changeItemNum(num: Int) {
        selectManager.itemNum = num
    }

    override fun click(
        id: Int,
        goNext: () -> Unit,
    ) {
        // 選択されていたらコールバック
        if (selectManager.selected == id) {
            goNext.invoke()
            return
        }

        //　今クリックしたやつを選択
        selectManager.selected = id
    }
}
