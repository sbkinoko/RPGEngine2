package menu.domain

import controller.domain.ArrowCommand

class SelectManager(
    val width: Int,
    private val itemNum: Int,
) {
    private var _selected: Int = 0
    var selected: Int
        get() = _selected
        set(value) {
            _selected = value
        }

    fun move(command: ArrowCommand) {
        when (command) {
            ArrowCommand.Up -> moveUp()
            ArrowCommand.Down -> moveDown()
            ArrowCommand.Left -> moveLeft()
            ArrowCommand.Right -> moveRight()
            else -> Unit
        }
    }

    private fun moveUp() {
        if (_selected < width) {
            //　同じ列の一番下を選択
            var newSelected = itemNum - 1
            while (newSelected % width != _selected) {
                newSelected--
            }
            _selected = newSelected
        } else {
            //　同じ列の一つ上を選択
            _selected -= width
        }
    }

    private fun moveDown() {
        if (itemNum - width <= _selected) {
            // 同じ列の一番上を選択
            _selected %= width
        } else {
            //　同じ列の一つ下を選択
            _selected += width
        }
    }

    private fun moveLeft() {
        if (_selected % width == 0) {
            _selected += width - 1
        } else {
            _selected--
        }
    }

    private fun moveRight() {
        if (_selected % width == width - 1) {
            _selected -= width - 1
        } else {
            _selected++
        }
    }
}
