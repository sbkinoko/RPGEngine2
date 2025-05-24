package data.monster

import core.domain.status.DropItemInfo
import core.domain.status.StatusData
import core.domain.status.monster.ActionStyle
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.statusParameter.StatusParameter
import core.domain.status.param.statusParameterWithMax.HP
import core.domain.status.param.statusParameterWithMax.MP
import data.item.skill.SkillId
import data.item.tool.ToolId

class MonsterRepositoryImpl : MonsterRepository {
    override fun getMonster(id: Int): MonsterStatus {
        // fixme モンスターの種類を増やす
        return MonsterStatus(
            statusData = StatusData(
                "花",
                hp = HP(
                    maxValue = 10,
                ),
                mp = MP(
                    maxValue = 10,
                ),
                speed = StatusParameter(10),
                conditionList = emptyList(),
            ),
            imgId = 1,
            exp = 2,
            money = 1,
            dropInfoList = listOf(
                DropItemInfo(
                    toolId = ToolId.HEAL1,
                    probability = 30,
                ),
                DropItemInfo(
                    toolId = ToolId.HEAL2,
                    probability = 10,
                ),
            ),
            skillList = listOf(
                SkillId.Normal1,
                SkillId.Normal2,
            ),
            actionStyle = ActionStyle.RANDOM,
        )
    }
}
