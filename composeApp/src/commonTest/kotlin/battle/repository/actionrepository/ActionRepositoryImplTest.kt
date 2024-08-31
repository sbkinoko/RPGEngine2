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
        val target = 1
        val targetNum = 1
        val actionType = ActionType.Normal

        actionRepository.setAction(
            playerId = playerID,
            actionType = actionType,
            targetNum = targetNum
        )

        actionRepository.getAction(playerID).apply {
            assertEquals(
                expected = actionType,
                actual = thisTurnAction,
            )
            assertEquals(
                expected = targetNum,
                actual = this.targetNum,
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
            assertEquals(
                expected = targetNum,
                actual = this.targetNum,
            )
        }
    }

    @Test
    fun setSkill() {
        val playerID = 2
        val skillID = 1
        val targetNum = 2
        actionRepository.setAction(
            playerId = playerID,
            actionType = ActionType.Skill,
            skillId = skillID,
            targetNum = targetNum,
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
            assertEquals(
                expected = targetNum,
                actual = this.targetNum,
            )
        }
    }

    @Test
    fun setNormalAfterSkill() {
        val playerID = 2
        val skillID = 1
        val initTargetNum = 1
        val initAction = ActionType.Skill
        val secondTargetNum = 2
        val secondAction = ActionType.Normal

        actionRepository.setAction(
            playerId = playerID,
            actionType = initAction,
            skillId = skillID,
            targetNum = initTargetNum,
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
            assertEquals(
                expected = initTargetNum,
                actual = this.targetNum,
            )
        }

        actionRepository.setAction(
            playerId = playerID,
            actionType = secondAction,
            skillId = skillID,
            targetNum = secondTargetNum,
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
            assertEquals(
                expected = secondTargetNum,
                actual = this.targetNum,
            )
        }
    }

    @Test
    fun getNotSetPlayerId() {
        val playerId = 1
        actionRepository.getAction(
            playerId = playerId,
        ).apply {
            assertEquals(
                expected = ActionRepository.INITIAL_TARGET,
                actual = this.target,
            )
        }
    }

    @Test
    fun setTargetTest() {
        val playerID = 1
        val target = 1
        val actionType = ActionType.Normal
        val targetNum = 1

        // nullになっちゃうのでセットしておく
        actionRepository.setAction(
            playerId = playerID,
            actionType = actionType,
            targetNum = targetNum
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
        val init = 1
        val playerID = 1
        val targetNum = 1

        actionRepository.setAction(
            playerId = playerID,
            actionType = ActionType.Normal,
            targetNum = targetNum,
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
            expected = ActionRepository.INITIAL_TARGET,
            actual = actionRepository.getAction(
                playerId = playerID,
            ).target,
        )
    }
}
