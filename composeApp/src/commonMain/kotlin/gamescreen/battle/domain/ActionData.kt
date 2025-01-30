package gamescreen.battle.domain

data class ActionData(
    val thisTurnAction: ActionType = ActionType.Normal,
    val lastSelectedAction: ActionType = ActionType.Normal,
    val target: Int = 0,
    val ally: Int = 0,
    val skillId: Int = 0,
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
                skillId = 0,
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
