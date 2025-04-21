package core.domain.status

import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import data.item.skill.SkillId
import data.item.tool.ToolId

data class PlayerStatus(
    override val statusData: StatusData,
    val skillList: List<SkillId>,
    val toolList: List<ToolId>,
    val exp: EXP,
) : Character {

    fun addStatus(
        statusIncrease: StatusIncrease,
    ): PlayerStatus {
        return statusIncrease.let {
            copy(
                statusData = statusData.copy(
                    hp = HP(
                        statusData.hp.maxValue + it.hp,
                    ),
                    mp = MP(
                        statusData.mp.maxValue + it.mp,
                    ),
                    speed = statusData.speed + it.speed,
                )
            )
        }
    }
}
