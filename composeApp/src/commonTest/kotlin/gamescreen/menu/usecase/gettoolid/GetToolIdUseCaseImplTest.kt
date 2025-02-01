package gamescreen.menu.usecase.gettoolid

import core.domain.status.PlayerStatus
import core.repository.player.PlayerStatusRepository
import data.item.skill.SkillId
import gamescreen.menu.domain.BagToolData
import gamescreen.menu.repository.bag.BagRepository
import kotlinx.coroutines.flow.StateFlow
import values.Constants
import kotlin.test.Test
import kotlin.test.assertEquals

class GetToolIdUseCaseImplTest {
    private var countPlayer = 0
    private val playerRepository = object : PlayerStatusRepository {
        override val playerStatusFlow: StateFlow<List<PlayerStatus>>
            get() = throw NotImplementedError()

        override fun getPlayers(): List<PlayerStatus> {
            throw NotImplementedError()
        }

        override fun getTool(playerId: Int, index: Int): Int {
            countPlayer++
            return 0
        }

        override fun getSkill(playerId: Int, index: Int): SkillId {
            throw NotImplementedError()
        }

        override fun getStatus(id: Int): PlayerStatus {
            throw NotImplementedError()
        }

        override suspend fun setStatus(id: Int, status: PlayerStatus) {
            throw NotImplementedError()
        }
    }

    private var countBag = 0
    private val bagRepository = object : BagRepository {
        override fun getList(): List<BagToolData> {
            throw NotImplementedError()
        }

        override fun getItemIdAt(index: Int): Int {
            countBag++
            return 0
        }

        override fun setData(data: BagToolData) {
            throw NotImplementedError()
        }
    }

    private val getToolIdUseCase = GetToolIdUseCaseImpl(
        playerStatusRepository = playerRepository,
        bagRepository = bagRepository
    )

    @Test
    fun getFromPlayer() {
        getToolIdUseCase.invoke(
            0,
            0,
        )

        assertEquals(
            expected = 1,
            actual = countPlayer,
        )

        assertEquals(
            expected = 0,
            actual = countBag,
        )
    }

    @Test
    fun getFromPlayer_Border() {
        getToolIdUseCase.invoke(
            Constants.playerNum - 1,
            0,
        )

        assertEquals(
            expected = 1,
            actual = countPlayer,
        )

        assertEquals(
            expected = 0,
            actual = countBag,
        )
    }

    @Test
    fun getFromBag() {
        getToolIdUseCase.invoke(
            Constants.playerNum,
            0,
        )

        assertEquals(
            expected = 0,
            actual = countPlayer,
        )

        assertEquals(
            expected = 1,
            actual = countBag,
        )
    }
}
