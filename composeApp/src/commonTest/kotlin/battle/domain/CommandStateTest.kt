package battle.domain

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandStateTest {

    private lateinit var commandState: CommandState

    @BeforeTest
    fun beforeTest() {
        commandState = CommandState()
    }

    @Test
    fun initialStateEmpty() {
        assertEquals(
            expected = 0,
            actual = commandState.commandStack.size
        )
    }

    @Test
    fun pushState() {
        commandState.push(MainCommand)

        assertEquals(
            expected = 1,
            actual = commandState.commandStack.size
        )
        assertEquals(
            expected = MainCommand,
            actual = commandState.commandStack.last()
        )

        val playerID = 1
        commandState.push(
            PlayerActionCommand(
                playerId = playerID,
            )
        )

        assertEquals(
            expected = 2,
            actual = commandState.commandStack.size
        )
        assertEquals(
            expected = PlayerActionCommand(
                playerId = playerID,
            ),
            actual = commandState.commandStack.last()
        )
    }

    @Test
    fun popState() {
        commandState.push(MainCommand)

        val playerID = 1
        commandState.push(
            PlayerActionCommand(
                playerId = playerID,
            )
        )

        commandState.pop()
        assertEquals(
            expected = 1,
            actual = commandState.commandStack.size
        )
        assertEquals(
            expected = MainCommand,
            actual = commandState.commandStack.last()
        )

        commandState.pop()
        assertEquals(
            expected = 1,
            actual = commandState.commandStack.size
        )
        assertEquals(
            expected = MainCommand,
            actual = commandState.commandStack.last()
        )
    }
}
