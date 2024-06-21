package common.status

import common.status.param.HP
import common.status.param.MP

class PlayerStatus(
    override var name: String,
    override val hp: HP,
    override val mp: MP,
) : Status
