package menu.main

import common.Timer
import controller.domain.Command
import controller.domain.ControllerCallback
import controller.domain.StickPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainMenuViewModel : ControllerCallback {

    private val mutableSelectedFlow = MutableStateFlow<Int>(0)
    val selectedFlow: StateFlow<Int> = mutableSelectedFlow.asStateFlow()

    fun setSelected(selected: Int) {
        mutableSelectedFlow.value = selected
    }

    private val timer = Timer(200)

    lateinit var pairedList: MutableList<Pair<MainMenuItem, MainMenuItem?>>
    lateinit var list: List<MainMenuItem>

    private var itemNum = 0

    fun setItems(items: List<MainMenuItem>) {
        itemNum = items.size
        list = items
        pairedList = mutableListOf()
        for (cnt: Int in items.indices step 2) {
            if (cnt + 1 < items.size) {
                //　次の項目とセットで追加
                pairedList.add(Pair(items[cnt], items[cnt + 1]))
            } else {
                // もう項目がないのでnullを入れる
                pairedList.add(Pair(items[cnt], null))
            }
        }
    }

    // 専用クラスに抽出したい
    private fun moveHorizontal() {
        if (selectedFlow.value % 2 == 0) {
            // 最後のペアになってないアイテムなら移動しない
            if (selectedFlow.value == itemNum - 1) {
                return
            }
            setSelected(selectedFlow.value + 1)
        } else {
            setSelected(selectedFlow.value - 1)
        }
    }

    private fun moveUp() {
        if (2 <= selectedFlow.value) {
            setSelected(selectedFlow.value - 2)
            return
        }

        if (itemNum % 2 == 0) {
            if (selectedFlow.value == 0) {
                setSelected(itemNum - 2)
            } else {
                setSelected(itemNum - 1)
            }
        } else {
            if (selectedFlow.value == 0) {
                setSelected(itemNum - 1)
            } else {
                setSelected(itemNum - 2)
            }
        }
    }

    private fun moveDown() {
        if (selectedFlow.value < itemNum - 2) {
            setSelected(selectedFlow.value + 2)
            return
        }

        if (selectedFlow.value % 2 == 0) {
            setSelected(0)
        } else {
            setSelected(1)
        }
    }

    override fun moveStick(stickPosition: StickPosition) {
        if (!timer.isNeedTimePassed()) return

        when (stickPosition.toCommand()) {
            Command.Left,
            Command.Right -> {
                moveHorizontal()
            }

            Command.Up -> {
                moveUp()
            }

            Command.Down -> {
                moveDown()
            }

            else -> Unit
        }
    }

    override var pressA: () -> Unit = {
        list[selectedFlow.value].onClick()
    }
    override var pressB: () -> Unit = {

    }
    override var pressM: () -> Unit = {

    }
}
