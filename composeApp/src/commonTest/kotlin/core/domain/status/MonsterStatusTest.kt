package core.domain.status

import core.domain.status.monster.ActionStyle
import core.domain.status.monster.MonsterStatus
import data.item.skill.SkillId
import data.item.tool.ToolId

class MonsterStatusTest {
    companion object {

        val TestActiveMonster
            get() = MonsterStatus(
                imgId = 1,
                statusData = StatusDataTest.enemyStatus.setHP(
                    10
                ),
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
                statusData = StatusDataTest.enemyStatus.setHP(
                    0,
                )
            )
    }
}
