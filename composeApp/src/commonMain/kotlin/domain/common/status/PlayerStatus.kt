package domain.common.status

import domain.common.status.param.HP
import domain.common.status.param.MP

class PlayerStatus(
    override var name: String,
    override val hp: HP,
    override val mp: MP,
) : Status
