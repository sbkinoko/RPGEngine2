package battle.repository.actionrepository

import battle.repository.action.ActionRepository
import battle.repository.action.ActionRepositoryImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class ActionRepositoryImplTest {
    private lateinit var actionRepository: ActionRepository

    @BeforeTest
    fun beforeTest() {
        actionRepository = ActionRepositoryImpl()
    }

    @Test
    fun setActionTest() {
        val playerID = 1
        val target = listOf(1)
        actionRepository.setAction(
            playerId = playerID,
            target = target,
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = target,
                actual = this.target,
            )
        }

        val playerID2 = 2
        val target2 = listOf(2)
        actionRepository.setAction(
            playerId = playerID2,
            target = target2,
        )

        actionRepository.getAction(playerID2).apply {
            assertEquals(
                expected = target2,
                actual = this.target,
            )
        }
    }

    @Test
    fun updateActionTest() {
        val playerID = 1
        val target = listOf(1)
        actionRepository.setAction(
            playerId = playerID,
            target = target,
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = target,
                actual = this.target,
            )
        }

        val target2 = listOf(2)
        actionRepository.setAction(
            playerId = playerID,
            target = target2,
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = target2,
                actual = this.target,
            )
        }
    }

    @Test
    fun getNotSetPlayerId() {
        actionRepository.getAction(1).apply {
            assertEquals(
                expected = listOf(0),
                actual = this.target,
            )
        }
    }

    @Test
    fun resetTargetTest() {
        val init = listOf(1, 2, 3)
        actionRepository.setAction(1, target = init)
        assertEquals(
            expected = init,
            actual = actionRepository.getAction(1).target,
        )

        actionRepository.resetTarget()
        assertEquals(
            expected = listOf(0),
            actual = actionRepository.getAction(1).target,
        )
    }
}
