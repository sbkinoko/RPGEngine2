package battle.repository.action

import battle.domain.ActionData
import battle.domain.ActionType

class ActionRepositoryImpl : ActionRepository {
    private val actionMap: MutableMap<Int, ActionData> = mutableMapOf()

    override fun setAction(
        playerId: Int,
        actionType: ActionType,
        target: List<Int>,
        skillId: Int?
    ) {
        actionMap[playerId] = actionMap[playerId]?.let { actionData ->
            // 共通の更新
            actionData.copy(
                thisTurnAction = actionType,
                target = target,
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
            target = target,
            skillId = skillId,
        )
    }

    override fun getAction(playerId: Int): ActionData {
        return actionMap[playerId] ?: ActionData.default
    }

    override fun resetTarget() {
        actionMap.forEach {
            actionMap[it.key] = actionMap[it.key]!!.copy(
                target = listOf(0)
            )
        }
    }
}
