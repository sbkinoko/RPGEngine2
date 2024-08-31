package battle.domain

data class ActionData(
    val thisTurnAction: ActionType = ActionType.Normal,
    val target: Int = 0,
    val targetNum: Int = 0,
    val skillId: Int? = null,
)

enum class ActionType {
    Normal,
    Skill
}
