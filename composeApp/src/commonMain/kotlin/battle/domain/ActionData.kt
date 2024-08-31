package battle.domain

data class ActionData(
    val thisTurnAction: ActionType,
    val target: List<Int>,
    val skillId: Int?,
) {
    companion object {
        val default: ActionData
            get() = ActionData(
                thisTurnAction = ActionType.Normal,
                target = listOf(0),
                skillId = null
            )
    }
}

enum class ActionType {
    Normal,
    Skill
}
