package gamescreen.battle.service.isannihilation

import common.status.MonsterStatusTest.Companion.getNotActiveTestMonster
import common.status.MonsterStatusTest.Companion.getTestMonster
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
                getTestMonster(),
                getTestMonster(),
                getTestMonster(),
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
                getTestMonster(),
                getNotActiveTestMonster(),
                getNotActiveTestMonster(),
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
                getNotActiveTestMonster(),
                getNotActiveTestMonster(),
                getNotActiveTestMonster(),
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
