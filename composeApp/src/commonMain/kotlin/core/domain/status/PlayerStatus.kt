package core.domain.status

import core.domain.status.param.HP
import core.domain.status.param.MP

data class PlayerStatus(
    override var name: String,
    override val hp: HP,
    override val mp: MP,
    // todo battleでも参照するようにする
    val skillList: List<Int>,
) : Status
