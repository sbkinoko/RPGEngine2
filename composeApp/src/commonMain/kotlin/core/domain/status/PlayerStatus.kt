package core.domain.status

import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP

data class PlayerStatus(
    override var name: String,
    override val hp: HP = dummyHP,
    override val mp: MP = dummyMP,
    override val speed: Int = 0,
    val skillList: List<Int>,
    val toolList: List<Int>,
    val exp: EXP,
) : Status

private val dummyHP
    get() = HP(
        0, 0,
    )

private val dummyMP
    get() = MP(
        0, 0
    )
