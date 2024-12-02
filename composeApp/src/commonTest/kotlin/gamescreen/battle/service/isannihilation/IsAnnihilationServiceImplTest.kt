package gamescreen.battle.service.isannihilation

import common.status.MonsterStatusTest.Companion.getMonster
import common.status.MonsterStatusTest.Companion.getNotActiveMonster
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

class IsAnnihilationServiceImplTest : KoinTest {
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

    @Test
    fun checkMonsterActive() {
        runBlocking {
            val activeMonsterList = listOf(
                getMonster(),
                getMonster(),
                getMonster(),
            )

            assertEquals(
                expected = false,
                actual = isAnnihilationService(
                    activeMonsterList
                )
            )
        }
    }

    @Test
    fun checkMonsterInActive() {
        runBlocking {
            val activeMonsterList = listOf(
                getNotActiveMonster(),
                getNotActiveMonster(),
                getNotActiveMonster(),
            )

            assertEquals(
                expected = true,
                actual = isAnnihilationService(
                    activeMonsterList
                )
            )
        }
    }

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
