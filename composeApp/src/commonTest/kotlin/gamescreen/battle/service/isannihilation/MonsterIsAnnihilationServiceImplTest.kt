package gamescreen.battle.service.isannihilation

import common.status.MonsterStatusTest.Companion.getMonster
import common.status.MonsterStatusTest.Companion.getNotActiveMonster
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

    /**
     * モンスターがactiveとinactiveの場合の全滅チェック
     */
    @Test
    fun checkMonsterActiveAndNotActive() {
        runBlocking {
            val activeMonsterList = listOf(
                getMonster(),
                getNotActiveMonster(),
                getNotActiveMonster(),
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
}
