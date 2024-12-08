package core.domain.status

import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP

data class PlayerStatus(
    override var name: String,
    override val hp: HP,
    override val mp: MP,
    val skillList: List<Int>,
    val toolList: List<Int>,
    val exp: EXP,
) : Status
