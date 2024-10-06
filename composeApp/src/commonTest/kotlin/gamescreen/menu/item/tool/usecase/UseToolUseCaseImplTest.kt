package gamescreen.menu.item.tool.usecase

import core.domain.Place
import core.domain.item.TargetType
import core.domain.item.tool.HealTool
import core.domain.item.tool.Tool
import core.domain.status.PlayerStatus
import core.repository.item.tool.ToolRepository
import core.usecase.updateparameter.UpdateStatusUseCase
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.skill.repository.target.TargetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UseToolUseCaseImplTest : KoinTest {
    private lateinit var useToolUseCase: UseToolUseCase

    private val targetRepository: TargetRepository = object : TargetRepository {
        override var target: Int = 0
    }

    private val useItemIdRepository: UseItemIdRepository = object : UseItemIdRepository {
        override var itemId: Int = 0
    }

    private val toolRepository: ToolRepository = object : ToolRepository {
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

    @Test
    fun updateHP() {
        var count = 0
        val updateStatusService = object : UpdateStatusUseCase<PlayerStatus> {
            override suspend fun decHP(id: Int, amount: Int) {
                throw NotImplementedError()
            }

            override suspend fun incHP(id: Int, amount: Int) {
                count++
            }

            override suspend fun decMP(id: Int, amount: Int) {
                throw NotImplementedError()
            }

            override suspend fun incMP(id: Int, amount: Int) {
                throw NotImplementedError()
            }
        }

        runBlocking {
            useToolUseCase = UseToolUseCaseImpl(
                targetRepository = targetRepository,
                useItemIdRepository = useItemIdRepository,
                toolRepository = toolRepository,
                updateStatusService = updateStatusService,
            )

            useToolUseCase.invoke()

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )
        }
    }
}
