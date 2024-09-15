package main.status

import main.status.param.HP
import main.status.param.MP

data class PlayerStatus(
    override var name: String,
    override val hp: HP,
    override val mp: MP,
) : Status
