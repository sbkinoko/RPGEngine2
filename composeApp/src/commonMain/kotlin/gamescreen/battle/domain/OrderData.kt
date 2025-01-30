package gamescreen.battle.domain

import core.domain.status.Status

data class OrderData(
    val id: Int,
    val status: Status,
    val actionData: ActionData,
)
