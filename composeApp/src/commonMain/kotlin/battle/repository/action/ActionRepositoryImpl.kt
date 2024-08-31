package battle.repository.action

import battle.domain.ActionData
import battle.domain.ActionType

class ActionRepositoryImpl : ActionRepository {
    private val actionMap: MutableMap<Int, ActionData> = mutableMapOf()

    override fun setAction(
        playerId: Int,
        actionType: ActionType,
        targetNum: Int,
        skillId: Int?
    ) {
        actionMap[playerId] = actionMap[playerId]?.let { actionData ->
            // 共通の更新
            actionData.copy(
                thisTurnAction = actionType,
                targetNum = targetNum,
            ).let {
                when (actionType) {
                    ActionType.Normal -> it
                    ActionType.Skill -> it.copy(
                        skillId = skillId,
                    )
                }
            }
        } ?: ActionData(
            thisTurnAction = actionType,
            skillId = skillId,
            targetNum = targetNum,
        )
    }

    override fun setTarget(
        playerId: Int,
        target: Int,
    ) {
        // actionが設定されているはずなのでnullにはならない
        actionMap[playerId] = actionMap[playerId]!!.copy(
            target = target
        )
    }

    override fun getAction(playerId: Int): ActionData {
        return actionMap[playerId] ?: ActionData()
    }

    override fun resetTarget() {
        actionMap.forEach {
            actionMap[it.key] = actionMap[it.key]!!.copy(
                target = ActionRepository.INITIAL_TARGET
            )
        }
    }
}
