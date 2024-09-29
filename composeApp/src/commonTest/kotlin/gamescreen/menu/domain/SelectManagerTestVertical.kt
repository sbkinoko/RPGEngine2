package menu.domain

import controller.domain.ArrowCommand
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectManagerTestVertical {
    @Test
    fun checkMoveDown_3() {
        val selectManager = SelectManager(3, 9)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Down)
        assertEquals(
            expected = 3,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Down)
        assertEquals(
            expected = 6,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Down)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )
    }

    @Test
    fun checkMoveDown_1() {
        val selectManager = SelectManager(9, 9)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Down)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )
    }

    @Test
    fun checkMoveUp_3() {
        val selectManager = SelectManager(3, 9)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Up)
        assertEquals(
            expected = 6,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Up)
        assertEquals(
            expected = 3,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Up)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )
    }

    @Test
    fun checkMoveUp_1() {
        val selectManager = SelectManager(9, 9)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Up)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )
    }

    @Test
    fun checkMoveWithLack() {
        // 0 1 2
        // 3 4 5
        // 6 7
        val selectManager = SelectManager(3, 8)
        selectManager.selected = 2

        selectManager.move(ArrowCommand.Up)
        assertEquals(
            expected = 5,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Down)
        assertEquals(
            expected = 2,
            actual = selectManager.selected,
        )

        selectManager.selected = 1

        selectManager.move(ArrowCommand.Up)
        assertEquals(
            expected = 7,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Down)
        assertEquals(
            expected = 1,
            actual = selectManager.selected,
        )
    }
}
