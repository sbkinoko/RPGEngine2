package gamescreen.battle.domain

import core.domain.status.Character

data class StatusWrapper(
    val id: Int,
    val status: Character,
    val actionData: ActionData,
    val statusType: StatusType,
)
