package gamescreen.menu.domain

import controller.domain.ArrowCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectManager(
    val width: Int,
    private val itemNum: Int,
) {
    private var _selected: Int = FIRST_SELECTED

    private val mutableSelectedStateFlow =
        MutableStateFlow<Int>(FIRST_SELECTED)
    val selectedFlowState = mutableSelectedStateFlow.asStateFlow()

    var selected: Int
        get() = _selected
        set(value) {
            _selected = value
            mutableSelectedStateFlow.value = _selected
        }

    fun move(command: ArrowCommand) {
        when (command) {
            ArrowCommand.Up -> moveUp()
            ArrowCommand.Down -> moveDown()
            ArrowCommand.Left -> moveLeft()
            ArrowCommand.Right -> moveRight()
            else -> return
        }
    }

    private fun moveUp() {
        if (selected < width) {
            //　同じ列の一番下を選択
            var newSelected = itemNum - 1
            while (newSelected % width != selected) {
                newSelected--
            }
            selected = newSelected
        } else {
            //　同じ列の一つ上を選択
            selected -= width
        }
    }

    private fun moveDown() {
        if (itemNum - width <= selected) {
            // 同じ列の一番上を選択
            selected %= width
        } else {
            //　同じ列の一つ下を選択
            selected += width
        }
    }

    private fun moveLeft() {
        if (selected % width == 0) {
            selected += width - 1
            if (itemNum <= selected) {
                selected = itemNum - 1
            }
        } else {
            selected--
        }
    }

    private fun moveRight() {
        // 右端にいたら一周
        if (selected % width == width - 1) {
            selected -= width - 1
        } else {
            //　一つ右
            selected++
            //　長さオーバーしたら左端
            if (itemNum <= selected) {
                selected = itemNum - itemNum % width
            }
        }
    }

    companion object {
        const val FIRST_SELECTED = 0
    }
}
