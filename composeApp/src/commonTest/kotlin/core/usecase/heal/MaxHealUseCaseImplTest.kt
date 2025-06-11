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
                    statusData = statusData.run {
                        copy(
                            hp = hp.set(
                                value = 0,
                            ),
                            mp = mp.set(
                                value = 0
                            )
                        )
                    },
                )
            }
        }

        override val playerStatusFlow: StateFlow<List<PlayerStatus>>
            get() = throw NotImplementedError()

        override fun getPlayers(): List<PlayerStatus> {
            return statusList
        }

        override fun getStatusList(): List<PlayerStatus> {
            return statusList
        }

        override fun getTool(
            playerId: Int,
            index: Int,
        ): ToolId {
            throw NotImplementedError()
        }

        override fun getSkill(
            playerId: Int,
            index: Int,
        ): SkillId {
            throw NotImplementedError()
        }

        override fun getStatus(id: Int): PlayerStatus {
            return statusList[id]
        }

        override suspend fun setStatus(
            id: Int,
            status: PlayerStatus,
        ) {
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
                it.statusData.apply {
                    assertEquals(
                        expected = hp.maxPoint,
                        actual = hp.point
                    )

                    assertEquals(
                        expected = mp.maxPoint,
                        actual = mp.point
                    )
                }
            }
        }
    }
}
