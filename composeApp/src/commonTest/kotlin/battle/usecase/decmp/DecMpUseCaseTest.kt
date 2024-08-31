package battle.usecase.decmp

import battle.BattleModule
import common.CommonModule
import common.repository.player.PlayerRepository
import common.status.PlayerStatus
import common.status.param.HP
import common.status.param.MP
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DecMpUseCaseTest : KoinTest {
    private val decMpUseCase: DecMpUseCase by inject()
    private val playerRepository: PlayerRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                CommonModule,
                BattleModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun decMp1() {
        val id = 0
        val mp = 10
        val amount = 10
        val player = player(mp)

        playerRepository.setPlayer(
            id = id,
            status = player,
        )

        decMpUseCase(
            playerId = id,
            amount = amount,
        )

        assertEquals(
            expected = mp - amount,
            actual = playerRepository.getPlayer(id).mp.value
        )
    }

    @Test
    fun decMp2() {
        val id = 1
        val mp = 100
        val amount = 49
        val player = player(mp)

        playerRepository.setPlayer(
            id = id,
            status = player,
        )

        decMpUseCase(
            playerId = id,
            amount = amount,
        )

        assertEquals(
            expected = mp - amount,
            actual = playerRepository.getPlayer(id).mp.value
        )
    }

    private fun player(mp: Int): PlayerStatus {
        return PlayerStatus(
            name = "test",
            hp = HP(
                value = 100,
                maxValue = 100,
            ),
            mp = MP(
                value = mp,
                maxValue = 100,
            )
        )
    }
}
