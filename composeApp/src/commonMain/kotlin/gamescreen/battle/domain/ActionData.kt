package gamescreen.battle.domain

data class ActionData(
    val thisTurnAction: ActionType = ActionType.Normal,
    val lastSelectedAction: ActionType = ActionType.Normal,
    val target: Int = 0,
    val ally: Int = 0,
    val skillId: Int? = null,
)

enum class ActionType {
    Normal,
    Skill,
    TOOL,
    None,
}
