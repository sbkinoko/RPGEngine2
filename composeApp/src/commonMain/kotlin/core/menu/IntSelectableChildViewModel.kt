package core.menu

import kotlinx.coroutines.flow.StateFlow

abstract class IntSelectableChildViewModel : SelectableChildViewModel<Int>() {

    override val selectedFlowState: StateFlow<Int>
        get() = selectManager.selectedFlowState

    override fun setSelected(id: Int) {
        selectManager.selected = id
    }

    override fun onClickItem(id: Int) {
        // 選択されていたらコールバック
        if (selectManager.selected == id) {
            goNext()
            return
        }

        //　今クリックしたやつを選択
        selectManager.selected = id
    }
}
