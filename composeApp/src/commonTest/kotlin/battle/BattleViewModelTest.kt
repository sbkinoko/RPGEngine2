package battle

import common.CommonModule
import common.status.MonsterStatusTest.Companion.getMonster
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BattleViewModelTest : KoinTest {
    private lateinit var battleViewModel: BattleViewModel


    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                BattleModule,
                CommonModule,
            )
        }

        battleViewModel = BattleViewModel()
        battleViewModel.pressB = {}
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }


    @Test
    fun checkIsBattleFinish() {
        runBlocking {
            battleViewModel.setMonsters(
                MutableList(3) {
                    getMonster()
                }
            )

            delay(100)

            val id = 0
            val damage = 10

            battleViewModel.attack(
                target = id,
                damage = damage
            )
            assertEquals(
                expected = false,
                actual = battleViewModel.isAllMonsterNotActive
            )

            // 隣を攻撃
            battleViewModel.attack(
                target = id,
                damage = damage
            )
            assertEquals(
                expected = false,
                actual = battleViewModel.isAllMonsterNotActive
            )

            // 隣の隣を攻撃
            battleViewModel.attack(
                target = id,
                damage = damage
            )

            assertEquals(
                expected = true,
                actual = battleViewModel.isAllMonsterNotActive
            )
        }
    }
}
