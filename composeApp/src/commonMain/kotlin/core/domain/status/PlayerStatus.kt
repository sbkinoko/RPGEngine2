package core.domain.status

import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import data.item.skill.SkillId
import data.item.tool.ToolId

data class PlayerStatus(
    override var name: String,
    override val hp: HP = dummyHP,
    override val mp: MP = dummyMP,
    override val speed: Int = 0,
    override val conditionList: List<ConditionType> = listOf(),
    val skillList: List<SkillId>,
    val toolList: List<ToolId>,
    val exp: EXP,
) : Status {

    fun addStatus(
        statusIncrease: StatusIncrease,
    ): PlayerStatus {
        return statusIncrease.let {
            copy(
                hp = HP(
                    hp.maxValue + it.hp,
                ),
                mp = MP(
                    mp.maxValue + it.mp,
                ),
                speed = speed + it.speed,
            )
        }
    }
}

private val dummyHP
    get() = HP(
        0, 0,
    )

private val dummyMP
    get() = MP(
        0, 0
    )
