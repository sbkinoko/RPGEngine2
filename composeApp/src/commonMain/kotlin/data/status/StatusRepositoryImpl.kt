package data.status

import core.domain.status.IncData
import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusIncrease
import core.domain.status.StatusType
import core.domain.status.param.EXP
import data.item.skill.SkillId
import data.item.tool.ToolId

class StatusRepositoryImpl : AbstractStatusRepository() {

    override val statusUpList: List<List<StatusIncrease>> = List(3) {
        when (it) {
            0 -> listOf(
                StatusIncrease(
                    hp = IncData(100),
                    mp = IncData(10),
                    speed = IncData(11),
                    atk = IncData(10),
                    def = IncData(0),
                ),
                StatusIncrease(
                    hp = IncData(100),
                    mp = IncData(10),
                    speed = IncData(10),
                    atk = IncData(5),
                    def = IncData(0),
                ),
            )

            1 -> listOf(
                StatusIncrease(
                    hp = IncData(100),
                    mp = IncData(111),
                    speed = IncData(9),
                    atk = IncData(5),
                    def = IncData(0),
                )
            )

            2 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 100,
                    speed = 9,
                    atk = 5,
                    def = 0,
                )
            )

            3 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 500,
                    speed = 9,
                    atk = 5,
                    def = 0,
                )
            )

            else -> {
                throw IllegalStateException()
            }
        }
    }

    override val statusBaseList: List<Pair<PlayerStatus, StatusData<StatusType.Player>>> = List(3) {
        when (it) {
            0 ->
                Pair(
                    PlayerStatus(
                        skillList = listOf(
                            SkillId.AttackToTwo,
                            SkillId.BufAtk,
                            SkillId.Heal,
                            SkillId.DeBufAtk,
                            SkillId.Revive,
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
                    ),
                    StatusData(
                        name = "test1",
                    )
                )

            1,
                -> Pair(
                PlayerStatus(
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
                ),

                StatusData(
                    name = "test2",
                ),
            )

            2 -> Pair(
                PlayerStatus(
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
                ),
                StatusData(
                    name = "test3",
                ),
            )

            3 -> Pair(
                PlayerStatus(
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
                ),
                StatusData<StatusType.Player>(
                    name = "MPたくさん",
                ),
            )

            else -> throw IllegalStateException()
        }
    }
}
