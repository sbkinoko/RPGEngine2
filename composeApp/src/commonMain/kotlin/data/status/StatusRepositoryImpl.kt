package data.status

import core.domain.status.PlayerStatus
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import data.item.skill.ATTACK_TO_2
import data.item.skill.CANT_USE
import data.item.skill.HEAL_SKILL
import data.item.skill.REVIVE_SKILL
import data.item.tool.ToolRepositoryImpl

class StatusRepositoryImpl : StatusRepositoryAbstract() {

    private val dummyHP
        get() = HP(
            0, 0,
        )

    private val dummyMP
        get() = MP(
            0, 0
        )

    override val statusUpList: List<List<StatusIncrease>> = List(3) {
        when (it) {
            0 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 10,
                ),
                StatusIncrease(
                    hp = 100,
                    mp = 10,
                ),
            )

            1 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 111,
                )
            )

            2 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 100,
                )
            )

            3 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 500,
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
                    hp = dummyHP,
                    mp = dummyMP,
                    skillList = listOf(
                        ATTACK_TO_2,
                        CANT_USE,
                        HEAL_SKILL,
                        REVIVE_SKILL,
                    ),
                    toolList = listOf(
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL2,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                    ),
                    exp = EXP(
                        EXP.type1,
                    ),
                )

            1 -> PlayerStatus(
                name = "test2",
                hp = dummyHP,
                mp = dummyMP,
                skillList = listOf(
                    ATTACK_TO_2,
                    CANT_USE,
                ),
                toolList = listOf(
                    ToolRepositoryImpl.HEAL_TOOL,
                    ToolRepositoryImpl.HEAL_TOOL,
                ),
                exp = EXP(
                    EXP.type1,
                ),
            )

            2 -> PlayerStatus(
                name = "test3",
                hp = dummyHP,
                mp = dummyMP,
                skillList = listOf(
                    HEAL_SKILL,
                    REVIVE_SKILL,
                ),
                toolList = listOf(
                    ToolRepositoryImpl.HEAL_TOOL,
                    ToolRepositoryImpl.HEAL_TOOL,
                    ToolRepositoryImpl.HEAL_TOOL,
                ),
                exp = EXP(
                    EXP.type1,
                ),
            )

            3 -> PlayerStatus(
                name = "MPたくさん",
                hp = dummyHP,
                mp = dummyMP,
                skillList = listOf(
                    HEAL_SKILL,
                    REVIVE_SKILL,
                ),
                toolList = listOf(
                    ToolRepositoryImpl.HEAL_TOOL,
                    ToolRepositoryImpl.HEAL_TOOL,
                    ToolRepositoryImpl.HEAL_TOOL,
                    ToolRepositoryImpl.HEAL_TOOL,
                ),
                exp = EXP(
                    EXP.type1,
                ),
            )

            else -> throw IllegalStateException()
        }
    }
}
