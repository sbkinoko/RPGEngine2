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
            expected = MainCommand,
            actual = commandState.nowState
        )
    }

    @Test
    fun pushState() {
        commandState.push(MainCommand)

        assertEquals(
            expected = MainCommand,
            actual = commandState.nowState
        )

        val playerID = 1
        commandState.push(
            PlayerActionCommand(
                playerId = playerID,
            )
        )

        assertEquals(
            expected = PlayerActionCommand(
                playerId = playerID,
            ),
            actual = commandState.nowState
        )
    }

    @Test
    fun popState() {
        commandState.push(MainCommand)

        val playerID1 = 1
        val playerCommand1 = PlayerActionCommand(
            playerId = playerID1,
        )
        commandState.push(
            playerCommand1
        )

        val playerID2 = 2
        val playerCommand2 = PlayerActionCommand(
            playerId = playerID2,
        )
        commandState.push(
            playerCommand2,
        )

        assertEquals(
            expected = playerCommand2,
            actual = commandState.nowState
        )

        commandState.pop()
        assertEquals(
            expected = playerCommand1,
            actual = commandState.nowState
        )

        commandState.pop()
        assertEquals(
            expected = MainCommand,
            actual = commandState.nowState
        )

        // MainCommandでpopしてもそのまま
        commandState.pop()
        assertEquals(
            expected = MainCommand,
            actual = commandState.nowState
        )
    }
}
