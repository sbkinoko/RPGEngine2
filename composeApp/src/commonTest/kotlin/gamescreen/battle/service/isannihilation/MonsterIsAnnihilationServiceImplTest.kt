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

class MonsterIsAnnihilationServiceImplTest : KoinTest {
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
     * モンスターがすべてactiveの場合の全滅チェック
     */
    @Test
    fun checkMonsterActive() {
        runBlocking {
            val activeMonsterList = listOf(
                StatusDataTest.TestEnemyStatusActive,
                StatusDataTest.TestEnemyStatusActive,
                StatusDataTest.TestEnemyStatusActive,
            )

            assertEquals(
                expected = false,
                actual = isAnnihilationService(
                    activeMonsterList
                )
            )
        }
    }

    /**
     * モンスターがactiveとinactiveの場合の全滅チェック
     */
    @Test
    fun checkMonsterActiveAndNotActive() {
        runBlocking {
            val activeMonsterList = listOf(
                StatusDataTest.TestEnemyStatusActive,
                StatusDataTest.TestEnemyStatusInActive,
                StatusDataTest.TestEnemyStatusInActive,
            )

            assertEquals(
                expected = false,
                actual = isAnnihilationService(
                    activeMonsterList
                )
            )
        }
    }

    /**
     * モンスターがすべてinactiveの場合の全滅チェック
     */
    @Test
    fun checkMonsterInActive() {
        runBlocking {
            val activeMonsterList = listOf(
                StatusDataTest.TestEnemyStatusInActive,
                StatusDataTest.TestEnemyStatusInActive,
                StatusDataTest.TestEnemyStatusInActive,
            )

            assertEquals(
                expected = true,
                actual = isAnnihilationService(
                    activeMonsterList
                )
            )
        }
    }
}
