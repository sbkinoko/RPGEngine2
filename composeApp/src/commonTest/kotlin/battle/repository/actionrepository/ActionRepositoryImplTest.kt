package battle.repository.actionrepository

import battle.domain.ActionType
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
    fun setNormal() {
        val playerID = 1
        val target = listOf(1)
        val actionType = ActionType.Normal

        actionRepository.setAction(
            playerId = playerID,
            actionType = actionType,
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = actionType,
                actual = thisTurnAction,
            )
        }

        actionRepository.setTarget(
            playerId = playerID,
            target = target,
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = target,
                actual = this.target,
            )
        }
    }

    @Test
    fun setSkill() {
        val playerID = 2
        val skillID = 1
        actionRepository.setAction(
            playerId = playerID,
            actionType = ActionType.Skill,
            skillId = skillID
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = ActionType.Skill,
                actual = thisTurnAction,
            )
            assertEquals(
                expected = skillID,
                actual = this.skillId,
            )
        }
    }

    @Test
    fun setNormalAfterSkill() {
        val playerID = 2
        val skillID = 1
        val initAction = ActionType.Skill
        val secondAction = ActionType.Normal

        actionRepository.setAction(
            playerId = playerID,
            actionType = initAction,
            skillId = skillID
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = initAction,
                actual = thisTurnAction,
            )
            assertEquals(
                expected = skillID,
                actual = this.skillId,
            )
        }

        actionRepository.setAction(
            playerId = playerID,
            actionType = secondAction,
            skillId = skillID
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = secondAction,
                actual = thisTurnAction,
            )
            assertEquals(
                expected = skillID,
                actual = this.skillId,
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
    fun setTargetTest() {
        val playerID = 1
        val target = listOf(1)
        val actionType = ActionType.Normal

        // nullになっちゃうのでセットしておく
        actionRepository.setAction(
            playerId = playerID,
            actionType = actionType,
        )

        actionRepository.setTarget(
            playerId = playerID,
            target = target,
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = target,
                actual = this.target,
            )
        }
    }

    @Test
    fun resetTargetTest() {
        val init = listOf(1, 2, 3)
        val playerID = 1
        actionRepository.setAction(
            playerId = playerID,
            actionType = ActionType.Normal,
        )

        actionRepository.setTarget(
            playerId = playerID,
            target = init,
        )
        assertEquals(
            expected = init,
            actual = actionRepository.getAction(
                playerId = playerID
            ).target,
        )

        actionRepository.resetTarget()
        assertEquals(
            expected = listOf(0),
            actual = actionRepository.getAction(
                playerId = playerID,
            ).target,
        )
    }
}
