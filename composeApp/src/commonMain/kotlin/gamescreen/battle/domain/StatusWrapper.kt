package gamescreen.battle.domain

import core.domain.status.StatusData
import core.domain.status.StatusType


data class StatusWrapper(
    val status: StatusData<*>,
    val actionData: ActionData,
    val statusType: StatusType,
    val newId: Int,
)
