package core.usecase.item.usetool

import core.domain.Place
import core.domain.item.BufEffect
import core.domain.item.CostType
import core.domain.item.TargetStatusType
import core.domain.item.TargetType
import core.domain.item.Tool
import core.domain.item.tool.HealTool
import core.domain.status.ConditionType
import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest
import core.repository.status.StatusRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import data.item.tool.ToolId
import data.item.tool.ToolRepository
import gamescreen.menu.usecase.bag.dectool.DecToolUseCase
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UseToolUseCaseImplTest : KoinTest {

    private lateinit var useToolUseCase: UseToolUseCase

    var countIncHP = 0
    var countDelTool = 0

    var countDecItem = 0
    private val decToolUseCase = object : DecToolUseCase {
        override fun invoke(
            itemId: ToolId,
            itemNum: Int,
        ) {
            countDecItem++
        }
    }

    private var countGetToolId = 0
    private val getToolIdUseCase = object : GetToolIdUseCase {
        override fun invoke(
            userId: Int,
            index: Int,
        ): ToolId {
            countGetToolId++
            return ToolId.None
        }
    }

    private val updateStatusService = object : UpdatePlayerStatusUseCase() {
        override fun decHPImpl(
            amount: Int,
            status: PlayerStatus,
        ): PlayerStatus {
            throw NotImplementedError()
        }

        override fun incHPImpl(
            amount: Int,
            status: PlayerStatus,
        ): PlayerStatus {
            countIncHP++
            return status
        }

        override fun decMPImpl(
            amount: Int,
            status: PlayerStatus,
        ): PlayerStatus {
            throw NotImplementedError()
        }

        override fun incMPImpl(
            amount: Int,
            status: PlayerStatus,
        ): PlayerStatus {
            throw NotImplementedError()
        }

        override suspend fun addCondition(
            id: Int,
            conditionType: ConditionType,
        ) {
            throw NotImplementedError()
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

        override suspend fun deleteToolAt(
            playerId: Int,
            index: Int,
        ) {
            countDelTool++
        }

        override val statusRepository: StatusRepository<PlayerStatus>
            get() = object : StatusRepository<PlayerStatus> {
                override suspend fun setStatus(
                    id: Int,
                    status: PlayerStatus,
                ) {
                    // NOP
                }

                override fun getStatus(id: Int): PlayerStatus {
                    return PlayerStatusTest.testActivePlayer
                }

                override fun getStatusList(): List<PlayerStatus> {
                    throw NotImplementedError()
                }
            }
    }

    @Test
    fun updateHP() {
        val toolRepository: ToolRepository = object : ToolRepository {
            override fun getItem(id: ToolId): Tool {
                return HealTool(
                    name = "回復",
                    targetNum = 1,
                    usablePlace = Place.BOTH,
                    costType = CostType.Consume,
                    isDisposable = true,
                    healAmount = 10,
                    targetStatusType = TargetStatusType.ACTIVE,
                    targetType = TargetType.Ally,
                )
            }
        }

        runBlocking {
            useToolUseCase = UseToolUseCaseImpl(
                toolRepository = toolRepository,
                updateStatusService = updateStatusService,
                decToolUseCase = decToolUseCase,
                getToolIdUseCase = getToolIdUseCase,
            )

            useToolUseCase.invoke(
                targetId = 0,
                index = 0,
                userId = 0,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = countIncHP,
            )

            assertEquals(
                expected = 1,
                actual = countDelTool,
            )

            assertEquals(
                expected = 0,
                actual = countDecItem,
            )

            assertEquals(
                expected = 1,
                actual = countGetToolId,
            )
        }
    }

    @Test
    fun reusable() {
        val toolRepository: ToolRepository = object : ToolRepository {
            override fun getItem(id: ToolId): Tool {
                return HealTool(
                    name = "回復",
                    targetNum = 1,
                    usablePlace = Place.BOTH,
                    costType = CostType.NotConsume,
                    isDisposable = true,
                    healAmount = 10,
                    targetStatusType = TargetStatusType.ACTIVE,
                    targetType = TargetType.Ally,
                )
            }
        }

        runBlocking {
            useToolUseCase = UseToolUseCaseImpl(
                toolRepository = toolRepository,
                updateStatusService = updateStatusService,
                decToolUseCase = decToolUseCase,
                getToolIdUseCase = getToolIdUseCase,
            )

            useToolUseCase.invoke(
                targetId = 0,
                index = 0,
                userId = 0,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = countGetToolId,
            )

            assertEquals(
                expected = 1,
                actual = countIncHP,
            )

            assertEquals(
                expected = 0,
                actual = countDelTool,
            )

            assertEquals(
                expected = 0,
                actual = countDecItem,
            )
        }
    }

    @Test
    fun useBag() {
        val toolRepository: ToolRepository = object : ToolRepository {
            override fun getItem(id: ToolId): Tool {
                return HealTool(
                    name = "回復",
                    targetNum = 1,
                    usablePlace = Place.BOTH,
                    costType = CostType.Consume,
                    isDisposable = true,
                    healAmount = 10,
                    targetStatusType = TargetStatusType.ACTIVE,
                    targetType = TargetType.Ally,
                )
            }
        }

        runBlocking {
            useToolUseCase = UseToolUseCaseImpl(
                toolRepository = toolRepository,
                updateStatusService = updateStatusService,
                decToolUseCase = decToolUseCase,
                getToolIdUseCase = getToolIdUseCase,
            )

            useToolUseCase.invoke(
                targetId = 0,
                index = 0,
                userId = 99,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = countGetToolId,
            )

            assertEquals(
                expected = 1,
                actual = countIncHP,
            )

            assertEquals(
                expected = 0,
                actual = countDelTool,
            )

            assertEquals(
                expected = 1,
                actual = countDecItem,
            )
        }
    }
}
