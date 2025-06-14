package gamescreen.battle.domain

import core.domain.status.Character
import core.domain.status.StatusType

// fixme StatusData入れるように修正
data class StatusWrapper(
    val status: Character<*>,
    val actionData: ActionData,
    val statusType: StatusType,
    val newId: Int,
)
