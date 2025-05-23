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
        return copy(
            statusData = statusData
                .incStatus(
                    statusIncrease
                )
        )
    }
}
