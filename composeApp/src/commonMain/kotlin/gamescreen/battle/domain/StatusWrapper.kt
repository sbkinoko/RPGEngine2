package gamescreen.battle.domain

import core.domain.status.Character
import core.domain.status.StatusType

data class StatusWrapper(
    val status: Character<*>,
    val actionData: ActionData,
    val statusType: StatusType,
    val newId: Int,
)
