package gamescreen.battle.service.isannihilation

import common.status.PlayerStatusTest.Companion.getInActivePlayer
import common.status.PlayerStatusTest.Companion.getPlayer
import gamescreen.battle.BattleModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerIsAnnihilationServiceImplTest : KoinTest {
    private val isAnnihilationService: IsAnnihilationService by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                BattleModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * プレイヤーがすべてactiveの場合の全滅チェック
     */
    @Test
    fun checkPlayerActive() {
        runBlocking {
            val activePlayerList = listOf(
                getPlayer(),
                getPlayer(),
                getPlayer(),
            )

            assertEquals(
                expected = false,
                actual = isAnnihilationService(
                    activePlayerList,
                )
            )
        }
    }

    /**
     * プレイヤーがactiveとinactiveの場合の全滅チェック
     */
    @Test
    fun checkPlayerActiveAndNotActive() {
        runBlocking {
            val activePlayerList = listOf(
                getPlayer(),
                getInActivePlayer(),
                getInActivePlayer(),
            )

            assertEquals(
                expected = false,
                actual = isAnnihilationService(
                    activePlayerList,
                )
            )
        }
    }

    /**
     * プレイヤーがすべてinactiveの場合の全滅チェック
     */
    @Test
    fun checkPlayerInActive() {
        runBlocking {
            val activePlayerList = listOf(
                getInActivePlayer(),
                getInActivePlayer(),
                getInActivePlayer(),
            )

            assertEquals(
                expected = true,
                actual = isAnnihilationService(
                    activePlayerList,
                )
            )
        }
    }
}
