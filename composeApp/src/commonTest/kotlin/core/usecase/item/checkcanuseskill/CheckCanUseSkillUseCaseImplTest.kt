package core.usecase.item.checkcanuseskill

import core.ModuleCore
import core.domain.Place
import core.domain.item.CostType
import core.domain.item.Skill
import core.domain.item.TargetType
import core.domain.item.skill.HealSkill
import core.domain.status.PlayerStatusTest
import core.service.CheckCanUseService
import data.item.skill.SkillId
import data.item.skill.SkillRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckCanUseSkillUseCaseImplTest : KoinTest {
    private lateinit var checkCanUseSkillUseCase: CheckCanUseSkillUseCase
    private val checkCanUseService: CheckCanUseService by inject()

    private val skill = HealSkill(
        name = "test",
        healAmount = 1,
        costType = CostType.MP(1),
        targetNum = 1,
        targetType = TargetType.ACTIVE,
        usablePlace = Place.MAP,
    )

    private val playerStatusWithMP = PlayerStatusTest.testActivePlayer

    private val playerStatusNoMP = playerStatusWithMP.run {
        copy(
            statusData = statusData.setMP(0),
        )
    }

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
            )
        }

        val skillRepository = object : SkillRepository {
            override fun getItem(id: SkillId): Skill {
                return skill
            }
        }
        checkCanUseSkillUseCase = CheckCanUseSkillUseCaseImpl(
            skillRepository = skillRepository,
            checkCanUseService = checkCanUseService,
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun cantUseByPlace() {
        val playerStatus = playerStatusWithMP

        val result = checkCanUseSkillUseCase.invoke(
            skillId = SkillId.Normal1,
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
            skillId = SkillId.Normal1,
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
            skillId = SkillId.Normal1,
            status = playerStatus,
            here = Place.MAP,
        )

        assertEquals(
            expected = core.domain.AbleType.Able,
            actual = result
        )
    }
}
