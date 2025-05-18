package core.domain.status

import core.domain.status.param.EXP
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
                statusData = statusData
                    .incMaxHP(
                        amount = it.hp,
                    )
                    .incMaxMP(
                        amount = it.mp,
                    )
                    // fixme incを作る
                    .copy(
                        speed = statusData.speed + it.speed,
                    )
            )
        }
    }
}
