package core.usecase.item.checkcanuseskill

import core.domain.Place
import core.domain.item.ItemKind
import core.domain.item.TargetType
import core.domain.item.skill.HealSkill
import core.domain.status.PlayerStatus
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import data.item.skill.SkillRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckCanUseSkillUseCaseImplTest {
    private lateinit var checkCanUseSkillUseCase: CheckCanUseSkillUseCase

    private val skill = HealSkill(
        id = 0,
        name = "test",
        healAmount = 1,
        needMP = 1,
        targetNum = 1,
        targetType = TargetType.ACTIVE,
        usablePlace = Place.MAP,
    )

    private val playerStatusWithMP = PlayerStatus(
        name = "test",
        hp = HP(maxValue = 10, value = 10),
        mp = MP(maxValue = 10, value = 10),
        skillList = listOf(),
        toolList = listOf(),
        exp = EXP(
            EXP.type1,
        ),
    )

    private val playerStatusNoMP = PlayerStatus(
        name = "test",
        hp = HP(maxValue = 10, value = 10),
        mp = MP(maxValue = 10, value = 0),
        skillList = listOf(),
        toolList = listOf(),
        exp = EXP(
            EXP.type1,
        ),
    )

    @BeforeTest
    fun beforeTest() {
        val skillRepository = object : SkillRepository {
            override fun getItem(id: Int): ItemKind.Skill {
                return skill
            }
        }
        checkCanUseSkillUseCase = CheckCanUseSkillUseCaseImpl(skillRepository)
    }

    @Test
    fun cantUseByPlace() {
        val playerStatus = playerStatusWithMP

        val result = checkCanUseSkillUseCase.invoke(
            skillId = 1,
            status = playerStatus,
            here = Place.BATTLE
        )

        assertEquals(
            expected = core.domain.AbleType.CANT_USE_BY_PLACE,
            actual = result
        )
    }

    @Test
    fun cantUseByMP() {
        val playerStatus = playerStatusNoMP

        val result = checkCanUseSkillUseCase.invoke(
            skillId = 1,
            status = playerStatus,
            here = Place.MAP,
        )

        assertEquals(
            expected = core.domain.AbleType.CANT_USE_BY_MP,
            actual = result
        )
    }

    @Test
    fun canUse() {
        val playerStatus = playerStatusWithMP

        val result = checkCanUseSkillUseCase.invoke(
            skillId = 1,
            status = playerStatus,
            here = Place.MAP,
        )

        assertEquals(
            expected = core.domain.AbleType.Able,
            actual = result
        )
    }
}
