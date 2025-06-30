package core.menu

import common.Timer
import org.koin.core.component.KoinComponent

abstract class SelectableChildViewModel :
    SelectableWindowViewModel(),
    KoinComponent {
    override var timer: Timer = Timer(awaitTime = 200L)

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
}
