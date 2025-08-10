package gamescreen.battle.repository.action

import data.repository.monster.item.skill.SkillId
import data.repository.monster.item.tool.ToolId
import gamescreen.battle.domain.ActionData
import gamescreen.battle.domain.ActionType

class ActionRepositoryImpl : ActionRepository {
    private val actionMap: MutableMap<Int, ActionData> = mutableMapOf()

    override fun setAction(
        playerId: Int,
        actionType: ActionType,
        itemId: Any?,
        itemIndex: Int?,
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
                        skillId = itemId!! as SkillId,
                    )

                    ActionType.TOOL -> it.copy(
                        lastSelectedAction = actionType,
                        toolId = itemId!! as ToolId,
                        toolIndex = itemIndex!!,
                    )

                    ActionType.None -> it
                }
            }
        } ?: ActionData(
            thisTurnAction = actionType,
            // fixme いい感じに修正する
            skillId = if (actionType == ActionType.Skill) itemId as? SkillId
                ?: SkillId.NONE else SkillId.NONE,
            toolId = if (actionType == ActionType.TOOL) itemId as? ToolId
                ?: ToolId.None else ToolId.None,
            toolIndex = if (actionType == ActionType.TOOL) itemIndex!! else 0,
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
        allyId: Int,
    ) {
        // actionが設定されているはずなのでnullにはならない
        actionMap[playerId] = actionMap[playerId]!!.copy(
            ally = allyId
        )
    }

    override fun getAction(playerId: Int): ActionData {
        return actionMap[playerId] ?: ActionData.default()
    }

    override fun getLastSelectAction(playerId: Int): ActionType {
        return actionMap[playerId]?.lastSelectedAction
            ?: ActionData.default().lastSelectedAction
    }

    override fun resetTarget() {
        actionMap.forEach {
            actionMap[it.key] = actionMap[it.key]!!.copy(
                target = ActionRepository.INITIAL_TARGET
            )
        }
    }
}
