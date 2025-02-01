package gamescreen.battle.domain

import core.domain.status.Status

data class StatusWrapper(
    val id: Int,
    val status: Status,
    val actionData: ActionData,
)
