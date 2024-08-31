package battle.domain

data class ActionData(
    val thisTurnAction: ActionType = ActionType.Normal,
    val target: List<Int> = listOf(0),
    val skillId: Int? = null,
)

enum class ActionType {
    Normal,
    Skill
}
