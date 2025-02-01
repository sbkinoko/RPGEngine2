package data.monster

import core.domain.status.DropItemInfo
import core.domain.status.monster.ActionStyle
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import data.item.skill.SkillId
import data.item.tool.ToolRepositoryImpl

class MonsterRepositoryImpl : MonsterRepository {
    override fun getMonster(id: Int): MonsterStatus {
        // fixme モンスターの種類を増やす
        return MonsterStatus(
            1, "花",
            hp = HP(
                maxValue = 10,
            ),
            mp = MP(
                maxValue = 10,
            ),
            speed = 10,
            exp = 2,
            money = 1,
            dropInfoList = listOf(
                DropItemInfo(
                    itemId = ToolRepositoryImpl.HEAL_TOOL,
                    probability = 30,
                ),
                DropItemInfo(
                    itemId = ToolRepositoryImpl.HEAL_TOOL2,
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
