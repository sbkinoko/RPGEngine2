package gamescreen.menu.item.tool.usetool

import core.domain.Place
import core.domain.item.TargetType
import core.domain.item.tool.HealTool
import core.domain.item.tool.Tool
import core.domain.status.PlayerStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.item.tool.ToolRepository
import core.repository.status.StatusRepository
import core.usecase.item.usetool.UseToolUseCase
import core.usecase.item.usetool.UseToolUseCaseImpl
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.repository.user.UserRepository
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
        toolList = listOf()
    )

    private lateinit var useToolUseCase: UseToolUseCase

    private val userRepository: UserRepository = object : UserRepository {
        override var userId: Int = 0
    }

    private val targetRepository: TargetRepository = object : TargetRepository {
        override var target: Int = 0
    }

    private val useItemIdRepository: UseItemIdRepository = object : UseItemIdRepository {
        override var itemId: Int = 0
    }

    private val indexRepository: IndexRepository = object : IndexRepository {
        override var index: Int = 0
    }

    var count = 0
    var countDel = 0

    private val updateStatusService = object : UpdatePlayerStatusUseCase() {
        override fun decHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            throw NotImplementedError()
        }

        override fun incHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            count++
            return status
        }

        override fun decMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            throw NotImplementedError()
        }

        override fun incMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
            throw NotImplementedError()
        }

        override suspend fun deleteToolAt(playerId: Int, index: Int) {
            countDel++
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
            override fun getItem(id: Int): Tool {
                return HealTool(
                    id = id,
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
            )

            useToolUseCase.invoke(
                targetId = 0,
                toolId = 0,
                index = 0,
                userId = 0,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )

            assertEquals(
                expected = 1,
                actual = countDel,
            )
        }
    }


    @Test
    fun reusable() {
        val toolRepository: ToolRepository = object : ToolRepository {
            override fun getItem(id: Int): Tool {
                return HealTool(
                    id = id,
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
            )

            useToolUseCase.invoke(
                targetId = 0,
                toolId = 0,
                index = 0,
                userId = 0,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )

            assertEquals(
                expected = 0,
                actual = countDel,
            )
        }
    }
}
