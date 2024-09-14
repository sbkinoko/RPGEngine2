package battle.repository.action

import battle.domain.ActionData
import battle.domain.ActionType

class ActionRepositoryImpl : ActionRepository {
    private val actionMap: MutableMap<Int, ActionData> = mutableMapOf()

    override fun setAction(
        playerId: Int,
        actionType: ActionType,
        skillId: Int?
    ) {
        actionMap[playerId] = actionMap[playerId]?.let { actionData ->
            // 共通の更新
            actionData.copy(

                thisTurnAction = actionType,
            ).let {
                when (actionType) {
                    ActionType.Normal -> it.copy(
                        lastSelectedAction = actionType,
                    )

                    ActionType.Skill -> it.copy(
                        lastSelectedAction = actionType,
                        skillId = skillId,
                    )

                    ActionType.None -> it
                }
            }
        } ?: ActionData(
            thisTurnAction = actionType,
            skillId = skillId,
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

    override fun setAlly(
        playerId: Int,
        allyId: Int
    ) {
        // actionが設定されているはずなのでnullにはならない
        actionMap[playerId] = actionMap[playerId]!!.copy(
            ally = allyId
        )
    }

    override fun getAction(playerId: Int): ActionData {
        return actionMap[playerId] ?: ActionData()
    }

    override fun getLastSelectAction(playerId: Int): ActionType {
        return actionMap[playerId]?.lastSelectedAction
            ?: ActionData().lastSelectedAction
    }

    override fun resetTarget() {
        actionMap.forEach {
            actionMap[it.key] = actionMap[it.key]!!.copy(
                target = ActionRepository.INITIAL_TARGET
            )
        }
    }
}
