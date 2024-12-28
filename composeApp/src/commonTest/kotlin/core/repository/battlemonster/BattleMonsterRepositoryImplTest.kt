package core.repository.battlemonster

import core.ModuleCore
import core.domain.status.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class BattleMonsterRepositoryImplTest : KoinTest {
    private val battleMonsterRepository: BattleMonsterRepository by inject()

    private val monster1 = MonsterStatus(
        imgId = 1,
        name = "monster1",
        hp = HP(
            maxValue = 10,
        ),
        mp = MP(
            maxValue = 10,
        ),
        money = 1,
        exp = 1,
    )
    private val monster2 = MonsterStatus(
        imgId = 1,
        name = "monster1",
        hp = HP(
            maxValue = 10,
        ),
        mp = MP(
            maxValue = 10,
        ),
        money = 1,
        exp = 1,
    )

    private val monsterList = listOf(
        monster1,
        monster2,
    )

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * モンスターリストの更新テスト
     */
    @Test
    fun setMonsterTest() {
        runBlocking {
            var count = 0
            val collectJob = launch {
                battleMonsterRepository.monsterListStateFLow.collect {
                    assertEquals(
                        expected = monsterList,
                        actual = it,
                    )

                    count++
                }
            }

            battleMonsterRepository.setMonsters(
                monsters = monsterList.toMutableList()
            )
            battleMonsterRepository.getStatus(0).apply {
                assertEquals(
                    expected = monster1,
                    actual = this,
                )
            }
            battleMonsterRepository.getStatus(1).apply {
                assertEquals(
                    expected = monster2,
                    actual = this,
                )
            }

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }
}
