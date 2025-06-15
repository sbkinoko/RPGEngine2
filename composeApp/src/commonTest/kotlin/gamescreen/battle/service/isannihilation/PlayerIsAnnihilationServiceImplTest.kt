package gamescreen.battle.service.isannihilation

import core.domain.status.StatusDataTest
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
                StatusDataTest.TestPlayerStatusActive,
                StatusDataTest.TestPlayerStatusActive,
                StatusDataTest.TestPlayerStatusActive,
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
                StatusDataTest.TestPlayerStatusActive,
                StatusDataTest.TestPlayerStatusInActive,
                StatusDataTest.TestPlayerStatusInActive,
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
                StatusDataTest.TestPlayerStatusInActive,
                StatusDataTest.TestPlayerStatusInActive,
                StatusDataTest.TestPlayerStatusInActive,
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
