package data.repository.monster

import core.domain.status.DropItemInfo
import core.domain.status.StatusData
import core.domain.status.monster.ActionStyle
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
import data.repository.item.skill.SkillId
import data.repository.item.tool.ToolId

class MonsterRepositoryImpl : MonsterRepository {
    override fun getMonster(id: Int): Pair<MonsterStatus, StatusData> {
        // fixme モンスターの種類を増やす
        return Pair(
            MonsterStatus(
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
            ),
            StatusData(
                "花",
                hp = StatusParameterWithMax(
                    maxPoint = 10,
                ),
                mp = StatusParameterWithMax(
                    maxPoint = 10,
                ),
                speed = StatusParameter(10),
                atk = StatusParameter(10),
                conditionList = emptyList(),
            ),
        )
    }
}
