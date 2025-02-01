package gamescreen.battle.domain

import data.item.skill.SkillId

// fixme デフォルト引数を削除する
data class ActionData(
    val thisTurnAction: ActionType = ActionType.Normal,
    val lastSelectedAction: ActionType = ActionType.Normal,
    val target: Int = 0,
    val ally: Int = 0,
    val skillId: SkillId = SkillId.NONE,
    val toolId: Int = 0,
    val toolIndex: Int = 0,
) {
    companion object {
        fun default(): ActionData {
            return ActionData(
                thisTurnAction = ActionType.Normal,
                lastSelectedAction = ActionType.Normal,
                target = 0,
                ally = 0,
                skillId = SkillId.NONE,
                toolId = 0,
                toolIndex = 0,
            )
        }
    }
}

enum class ActionType {
    Normal,
    Skill,
    TOOL,
    None,
}
