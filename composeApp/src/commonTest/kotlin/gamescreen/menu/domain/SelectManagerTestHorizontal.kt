package gamescreen.menu.domain

import controller.domain.ArrowCommand
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectManagerTestHorizontal {

    @Test
    fun checkMoveRight_3() {
        val selectManager = SelectManager(3, 10)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Right)

        assertEquals(
            expected = 1,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Right)
        assertEquals(
            expected = 2,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Right)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )
    }

    @Test
    fun checkMoveRight_1() {
        val selectManager = SelectManager(1, 10)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Right)

        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )
    }

    @Test
    fun checkMoveLeft_3() {
        val selectManager = SelectManager(3, 10)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Left)

        assertEquals(
            expected = 2,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Left)
        assertEquals(
            expected = 1,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Left)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )
    }

    @Test
    fun checkMoveLeft_1() {
        val selectManager = SelectManager(1, 10)
        assertEquals(
            expected = 0,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Left)

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
        selectManager.selected = 6

        selectManager.move(ArrowCommand.Left)
        assertEquals(
            expected = 7,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Right)
        assertEquals(
            expected = 6,
            actual = selectManager.selected,
        )

        selectManager.selected = 3

        selectManager.move(ArrowCommand.Left)
        assertEquals(
            expected = 5,
            actual = selectManager.selected,
        )

        selectManager.move(ArrowCommand.Right)
        assertEquals(
            expected = 3,
            actual = selectManager.selected,
        )
    }
}
