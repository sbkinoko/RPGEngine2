package gamescreen.battle.service.isannihilation

import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.PlayerStatusTest.Companion.testNotActivePlayer
import gamescreen.battle.ModuleBattle
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
                ModuleBattle,
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
                testActivePlayer,
                testActivePlayer,
                testActivePlayer,
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
                testActivePlayer,
                testNotActivePlayer,
                testNotActivePlayer,
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
                testNotActivePlayer,
                testNotActivePlayer,
                testNotActivePlayer,
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
