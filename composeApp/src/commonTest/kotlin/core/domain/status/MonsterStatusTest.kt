package core.domain.status

import core.domain.status.monster.ActionStyle
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import data.item.skill.SkillId
import data.item.tool.ToolId

class MonsterStatusTest {
    companion object {
        private const val MAX_HP = 10
        private const val MAX_MP = 10

        val TestActiveMonster
            get() = MonsterStatus(
                imgId = 1,
                name = "テスト",
                hp = HP(
                    maxValue = MAX_HP,
                ),
                mp = MP(
                    maxValue = MAX_MP,
                ),
                speed = 10,
                money = 1,
                exp = 1,
                dropInfoList = listOf(
                    DropItemInfo(
                        toolId = ToolId.HEAL1,
                        probability = 1,
                    )
                ),
                skillList = listOf(
                    SkillId.Normal1, SkillId.Normal2,
                ),
                actionStyle = ActionStyle.RANDOM,
            )

        val TestNotActiveMonster
            get() = TestActiveMonster.copy(
                hp = HP(
                    maxValue = MAX_MP,
                    value = 0,
                )
            )
    }
}
