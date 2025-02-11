package core.usecase.item.usetool

import core.domain.Place
import core.domain.item.TargetType
import core.domain.item.Tool
import core.domain.item.tool.HealTool
import core.domain.status.ConditionType
import core.domain.status.PlayerStatus
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
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
    private val TEST_STATUS = PlayerStatus(
        name = "test1",
        hp = HP(
            maxValue = 100,
            value = 50,
        ),
        mp = MP(
            maxValue = 10,
            value = 5,
        ),
        skillList = listOf(),
        toolList = listOf(),
        exp = EXP(
            EXP.type1,
        ),
    )

    private lateinit var useToolUseCase: UseToolUseCase

    var countIncHP = 0
    var countDelTool = 0

    var countDecItem = 0
    private val decToolUseCase = object : DecToolUseCase {
        override fun invoke(itemId: ToolId, itemNum: Int) {
            countDecItem++
        }
    }

    private var countGetToolId = 0
    private val getToolIdUseCase = object : GetToolIdUseCase {
        override fun invoke(userId: Int, index: Int): ToolId {
            countGetToolId++
            return ToolId.None
        }
    }

    private val updateStatusService = object : UpdatePlayerStatusUseCase() {
        override fun decHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            throw NotImplementedError()
        }

        override fun incHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            countIncHP++
            return status
        }

        override fun decMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            throw NotImplementedError()
        }

        override fun incMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            throw NotImplementedError()
        }

        override suspend fun setCondition(id: Int, conditionType: ConditionType) {
            throw NotImplementedError()
        }

        override suspend fun deleteToolAt(playerId: Int, index: Int) {
            countDelTool++
        }

        override val statusRepository: StatusRepository<PlayerStatus>
            get() = object : StatusRepository<PlayerStatus> {
                override suspend fun setStatus(id: Int, status: PlayerStatus) {
                    // NOP
                }

                override fun getStatus(id: Int): PlayerStatus {
                    return TEST_STATUS
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
                    isReusable = false,
                    isDisposable = true,
                    healAmount = 10,
                    targetType = TargetType.ACTIVE
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
                    isReusable = true,
                    isDisposable = true,
                    healAmount = 10,
                    targetType = TargetType.ACTIVE
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
                    isReusable = false,
                    isDisposable = true,
                    healAmount = 10,
                    targetType = TargetType.ACTIVE
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
