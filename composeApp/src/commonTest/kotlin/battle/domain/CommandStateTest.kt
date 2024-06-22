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
    fun initialState() {
        assertEquals(
            expected = MainCommand,
            actual = commandState.nowState
        )
    }

    @Test
    fun pushState() {
        val playerID = 1
        val playerCommand = PlayerActionCommand(
            playerId = playerID,
        )
        commandState = commandState.push(
            playerCommand,
        )

        assertEquals(
            expected = playerCommand,
            actual = commandState.nowState
        )
    }

    @Test
    fun popState() {
        val playerID1 = 1
        val playerCommand1 = PlayerActionCommand(
            playerId = playerID1,
        )
        commandState = commandState.push(
            playerCommand1
        )

        val playerID2 = 2
        val playerCommand2 = PlayerActionCommand(
            playerId = playerID2,
        )
        commandState = commandState.push(
            playerCommand2,
        )

        assertEquals(
            expected = playerCommand2,
            actual = commandState.nowState
        )

        commandState = commandState.pop()
        assertEquals(
            expected = playerCommand1,
            actual = commandState.nowState
        )

        commandState = commandState.pop()
        assertEquals(
            expected = MainCommand,
            actual = commandState.nowState
        )

        // MainCommandでpopしてもそのまま
        commandState = commandState.pop()
        assertEquals(
            expected = MainCommand,
            actual = commandState.nowState
        )
    }
}
