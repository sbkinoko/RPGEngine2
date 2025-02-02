package core.usecase.heal

import core.domain.status.PlayerStatus
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.repository.player.PlayerStatusRepository
import data.item.skill.SkillId
import data.item.tool.ToolId
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxHealUseCaseImplTest {
    private val playerStatusRepository = object : PlayerStatusRepository {
        private var statusList = MutableList(2) {
            testActivePlayer.run {
                copy(
                    hp = hp.copy(
                        value = 0,
                    ),
                    mp = mp.copy(
                        value = 0
                    )
                )
            }
        }

        override val playerStatusFlow: StateFlow<List<PlayerStatus>>
            get() = TODO("Not yet implemented")

        override fun getPlayers(): List<PlayerStatus> {
            return statusList
        }

        override fun getTool(playerId: Int, index: Int): ToolId {
            TODO("Not yet implemented")
        }

        override fun getSkill(playerId: Int, index: Int): SkillId {
            TODO("Not yet implemented")
        }

        override fun getStatus(id: Int): PlayerStatus {
            return statusList[id]
        }

        override suspend fun setStatus(id: Int, status: PlayerStatus) {
            this.statusList[id] = status
        }
    }

    private lateinit var maxHealUseCase: MaxHealUseCase

    @BeforeTest
    fun beforeTest() {
        maxHealUseCase = MaxHealUseCaseImpl(
            playerStatusRepository = playerStatusRepository,
        )
    }

    @Test
    fun check() {
        runBlocking {
            maxHealUseCase.invoke()

            playerStatusRepository.getPlayers().map {
                assertEquals(
                    expected = it.hp.maxValue,
                    actual = it.hp.value
                )

                assertEquals(
                    expected = it.mp.maxValue,
                    actual = it.mp.value
                )
            }
        }
    }
}
