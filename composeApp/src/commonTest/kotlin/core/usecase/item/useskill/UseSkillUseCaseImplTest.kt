package core.usecase.item.useskill

import core.domain.Place
import core.domain.item.BufEffect
import core.domain.item.CostType
import core.domain.item.DamageType
import core.domain.item.Skill
import core.domain.item.TargetStatusType
import core.domain.item.TargetType
import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.domain.status.ConditionType
import core.domain.status.PlayerStatus
import core.repository.player.PlayerStatusRepository
import core.usecase.item.useitem.UseItemUseCase
import core.usecase.item.useitem.UseSkillUseCaseImpl
import core.usecase.updateparameter.UpdateStatusUseCase
import data.item.skill.SkillId
import data.item.skill.SkillRepository
import data.item.tool.ToolId
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UseSkillUseCaseImplTest {
    interface TestPlayerStatusRepository : PlayerStatusRepository {
        override val playerStatusFlow: StateFlow<List<PlayerStatus>>
            get() = throw NotImplementedError()

        override fun getStatusList(): List<PlayerStatus> {
            throw NotImplementedError()
        }

        override fun getTool(
            playerId: Int,
            index: Int,
        ): ToolId {
            throw NotImplementedError()
        }

        override fun getStatus(id: Int): PlayerStatus {
            throw NotImplementedError()
        }

        override suspend fun setStatus(
            id: Int,
            status: PlayerStatus,
        ) {
            throw NotImplementedError()
        }
    }

    private lateinit var playerStatusRepository: TestPlayerStatusRepository

    abstract class TestUpdatePlayerStatusUseCase : UpdateStatusUseCase {

        override suspend fun decHP(
            id: Int,
            amount: Int,
        ) {
            throw NotImplementedError()
        }

        override suspend fun incHP(
            id: Int,
            amount: Int,
        ) {
            throw NotImplementedError()
        }

        override suspend fun incMP(
            id: Int,
            amount: Int,
        ) {
            throw NotImplementedError()
        }

        override suspend fun addCondition(
            id: Int,
            conditionType: ConditionType,
        ) {
            throw NotImplementedError()
        }
    }

    private lateinit var updatePlayerStatusUseCase: UpdateStatusUseCase

    private val testUseCase = object : TestUpdatePlayerStatusUseCase() {
        override suspend fun incHP(
            id: Int,
            amount: Int,
        ) {
            super.incHP(id, amount)
        }

        override suspend fun decMP(
            id: Int,
            amount: Int,
        ) {
        }

        override suspend fun updateConditionList(
            id: Int,
            conditionList: List<ConditionType>,
        ) {
        }

        override suspend fun addBuf(
            id: Int,
            buf: BufEffect,
        ) {
            throw NotImplementedError()
        }

        override suspend fun spendTurn(id: Int) {
            throw NotImplementedError()
        }
    }

    private lateinit var skillRepository: SkillRepository

    private val useSkillUseCase: UseItemUseCase
        get() = UseSkillUseCaseImpl(
            playerStatusRepository = playerStatusRepository,
            skillRepository = skillRepository,
            updateStatus = updatePlayerStatusUseCase,
        )

    @Test
    fun checkSkillId() {
        val testId = 1
        val testIndex = 2
        val targetId = 3
        var count = 0
        skillRepository = object : SkillRepository {
            override fun getItem(id: SkillId): Skill {
                return AttackSkill(
                    name = "",
                    targetNum = 1,
                    costType = CostType.MP(1),
                    usablePlace = Place.MAP,
                    damageType = DamageType.AtkMultiple(1),
                    targetType = TargetType.Enemy,
                    targetStatusType = TargetStatusType.ACTIVE,
                )
            }
        }

        playerStatusRepository = object : TestPlayerStatusRepository {
            override fun getSkill(
                playerId: Int,
                index: Int,
            ): SkillId {
                assertEquals(
                    actual = testId,
                    expected = playerId
                )

                assertEquals(
                    expected = testIndex,
                    actual = index,
                )
                count++
                return SkillId.NONE
            }
        }

        updatePlayerStatusUseCase = testUseCase

        runBlocking {
            useSkillUseCase.invoke(
                userId = testId,
                index = testIndex,
                targetId = targetId,
            )
        }

        assertEquals(
            expected = 1,
            actual = count,
        )
    }

    @Test
    fun checkSkill() {
        val testId = 1
        val testIndex = 2
        val targetId = 3
        val skillId = SkillId.NONE

        var count = 0
        skillRepository = object : SkillRepository {
            override fun getItem(id: SkillId): Skill {
                assertEquals(
                    actual = id,
                    expected = skillId,
                )
                count++

                return AttackSkill(
                    name = "",
                    targetNum = 1,
                    costType = CostType.MP(1),
                    usablePlace = Place.MAP,
                    damageType = DamageType.AtkMultiple(1),
                    targetType = TargetType.Enemy,
                    targetStatusType = TargetStatusType.ACTIVE,
                )
            }
        }

        playerStatusRepository = object : TestPlayerStatusRepository {
            override fun getSkill(
                playerId: Int,
                index: Int,
            ): SkillId {
                return skillId
            }
        }

        updatePlayerStatusUseCase = testUseCase

        runBlocking {
            useSkillUseCase.invoke(
                userId = testId,
                index = testIndex,
                targetId = targetId,
            )
        }

        assertEquals(
            expected = 1,
            actual = count,
        )
    }

    @Test
    fun checkDecMP() {
        val testId = 1
        val testIndex = 2
        val targetId = 3
        val skillId = SkillId.NONE

        var count = 0
        skillRepository = object : SkillRepository {
            override fun getItem(id: SkillId): Skill {
                return AttackSkill(
                    name = "",
                    targetNum = 1,
                    costType = CostType.MP(1),
                    usablePlace = Place.MAP,
                    damageType = DamageType.AtkMultiple(1),
                    targetType = TargetType.Enemy,
                    targetStatusType = TargetStatusType.ACTIVE,
                )
            }
        }

        playerStatusRepository = object : TestPlayerStatusRepository {
            override fun getSkill(
                playerId: Int,
                index: Int,
            ): SkillId {
                return skillId
            }
        }

        updatePlayerStatusUseCase = object : TestUpdatePlayerStatusUseCase() {
            override suspend fun decMP(
                id: Int,
                amount: Int,
            ) {
                count++
            }

            override suspend fun updateConditionList(
                id: Int,
                conditionList: List<ConditionType>,
            ) {
            }

            override suspend fun addBuf(
                id: Int,
                buf: BufEffect,
            ) {
                throw NotImplementedError()
            }

            override suspend fun spendTurn(id: Int) {
                throw NotImplementedError()
            }

            override suspend fun incHP(
                id: Int,
                amount: Int,
            ) {
            }
        }

        runBlocking {
            useSkillUseCase.invoke(
                userId = testId,
                index = testIndex,
                targetId = targetId,
            )
        }

        assertEquals(
            expected = 1,
            actual = count,
        )
    }

    @Test
    fun checkHealSkill() {
        val testId = 1
        val testIndex = 2
        val targetId = 3
        val skillId = SkillId.NONE

        var count = 0
        skillRepository = object : SkillRepository {
            override fun getItem(id: SkillId): Skill {
                return HealSkill(
                    name = "",
                    targetNum = 1,
                    costType = CostType.MP(1),
                    usablePlace = Place.MAP,
                    healAmount = 1,
                    targetStatusType = TargetStatusType.ACTIVE,
                    targetType = TargetType.Enemy,
                )
            }
        }

        playerStatusRepository = object : TestPlayerStatusRepository {
            override fun getSkill(
                playerId: Int,
                index: Int,
            ): SkillId {
                return skillId
            }
        }

        updatePlayerStatusUseCase = object : TestUpdatePlayerStatusUseCase() {
            override suspend fun incHP(
                id: Int,
                amount: Int,
            ) {
                count++
            }

            override suspend fun decMP(
                id: Int,
                amount: Int,
            ) {
            }

            override suspend fun updateConditionList(
                id: Int,
                conditionList: List<ConditionType>,
            ) {
                throw NotImplementedError()
            }

            override suspend fun addBuf(
                id: Int,
                buf: BufEffect,
            ) {
                throw NotImplementedError()
            }

            override suspend fun spendTurn(id: Int) {
                throw NotImplementedError()
            }
        }

        runBlocking {
            useSkillUseCase.invoke(
                userId = testId,
                index = testIndex,
                targetId = targetId,
            )
        }

        assertEquals(
            expected = 1,
            actual = count,
        )
    }

    @Test
    fun checkAttackSkill() {
        val testId = 1
        val testIndex = 2
        val targetId = 3
        val skillId = SkillId.NONE

        var count = 0
        skillRepository = object : SkillRepository {
            override fun getItem(id: SkillId): Skill {
                return AttackSkill(
                    name = "",
                    targetNum = 1,
                    costType = CostType.MP(1),
                    usablePlace = Place.MAP,
                    damageType = DamageType.AtkMultiple(1),
                    targetType = TargetType.Enemy,
                    targetStatusType = TargetStatusType.ACTIVE,
                )
            }
        }

        playerStatusRepository = object : TestPlayerStatusRepository {
            override fun getSkill(
                playerId: Int,
                index: Int,
            ): SkillId {
                return skillId
            }
        }

        updatePlayerStatusUseCase = object : TestUpdatePlayerStatusUseCase() {
            override suspend fun incHP(
                id: Int,
                amount: Int,
            ) {
                count++
            }

            override suspend fun decMP(
                id: Int,
                amount: Int,
            ) {
            }

            override suspend fun updateConditionList(
                id: Int,
                conditionList: List<ConditionType>,
            ) {
            }

            override suspend fun addBuf(
                id: Int,
                buf: BufEffect,
            ) {
                throw NotImplementedError()
            }

            override suspend fun spendTurn(id: Int) {
                throw NotImplementedError()
            }
        }

        runBlocking {
            useSkillUseCase.invoke(
                userId = testId,
                index = testIndex,
                targetId = targetId,
            )
        }

        assertEquals(
            expected = 0,
            actual = count,
        )
    }

    // todo 状態異常を変えるスキルを使った場合のテストを作る
}
