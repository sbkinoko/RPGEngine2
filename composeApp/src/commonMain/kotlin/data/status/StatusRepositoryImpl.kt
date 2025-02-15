package data.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusIncrease
import core.domain.status.param.EXP
import data.item.skill.SkillId
import data.item.tool.ToolId

class StatusRepositoryImpl : AbstractStatusRepository() {

    override val statusUpList: List<List<StatusIncrease>> = List(3) {
        when (it) {
            0 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 10,
                    speed = 11,
                ),
                StatusIncrease(
                    hp = 100,
                    mp = 10,
                    speed = 10
                ),
            )

            1 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 111,
                    speed = 9,
                )
            )

            2 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 100,
                    speed = 9,
                )
            )

            3 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 500,
                    speed = 9,
                )
            )

            else -> {
                throw IllegalStateException()
            }
        }
    }

    override val statusBaseList: List<PlayerStatus> = List(3) {
        when (it) {
            0 ->
                PlayerStatus(
                    name = "test1",
                    skillList = listOf(
                        SkillId.AttackToTwo,
                        SkillId.CantUse,
                        SkillId.Heal,
                        SkillId.Revive,
                        SkillId.Paralysis,
                        SkillId.Poison,
                    ),
                    toolList = listOf(
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL2,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                    ),
                    exp = EXP(
                        EXP.type1,
                    ),
                )

            1 -> PlayerStatus(
                name = "test2",
                skillList = listOf(
                    SkillId.AttackToTwo,
                    SkillId.CantUse,
                    SkillId.Paralysis,
                    SkillId.Poison,
                ),
                toolList = listOf(
                    ToolId.HEAL1,
                    ToolId.HEAL1,
                ),
                exp = EXP(
                    EXP.type1,
                ),
            )

            2 -> PlayerStatus(
                name = "test3",
                skillList = listOf(
                    SkillId.Heal,
                    SkillId.Revive,
                    SkillId.Paralysis,
                    SkillId.Poison,
                ),
                toolList = listOf(
                    ToolId.HEAL1,
                    ToolId.HEAL1,
                ),
                exp = EXP(
                    EXP.type1,
                ),
            )

            3 -> PlayerStatus(
                name = "MPたくさん",
                skillList = listOf(
                    SkillId.Heal,
                    SkillId.Revive,
                ),
                toolList = listOf(
                    ToolId.HEAL1,
                    ToolId.HEAL1,
                    ToolId.HEAL1,
                    ToolId.HEAL1,
                ),
                exp = EXP(
                    EXP.type1,
                ),
            )

            else -> throw IllegalStateException()
        }
    }
}
