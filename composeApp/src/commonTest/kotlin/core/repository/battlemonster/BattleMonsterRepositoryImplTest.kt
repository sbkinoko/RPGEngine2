package core.repository.battlemonster

import core.ModuleCore
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
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
    private val battleInfoRepository: BattleInfoRepository by inject()

    private val monster1 = TestActiveMonster
    private val monsterList1 = listOf(
        monster1,
    )

    private val monsterList2 = listOf(
        monster1,
        monster1,
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
    fun setMonsterTest1() {
        runBlocking {
            var count = 0
            val collectJob = launch {
                battleInfoRepository.monsterListStateFLow.collect {
                    count++
                }
            }

            battleInfoRepository.setMonsters(
                monsters = monsterList1.toMutableList()
            )

            delay(100)

            battleInfoRepository.getStatus(0).apply {
                assertEquals(
                    expected = monster1,
                    actual = this,
                )
            }

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * モンスターリストの更新テスト
     */
    @Test
    fun setMonsterTest2() {
        runBlocking {
            var count = 0
            val collectJob = launch {
                battleInfoRepository.monsterListStateFLow.collect {
                    count++
                }
            }

            battleInfoRepository.setMonsters(
                monsters = monsterList2.toMutableList()
            )

            delay(100)

            battleInfoRepository.getStatus(0).apply {
                assertEquals(
                    expected = monster1.run {
                        copy(
                            statusData = statusData.updateName(
                                name = statusData.name + "1"
                            )
                        )
                    },
                    actual = this,
                )
            }
            battleInfoRepository.getStatus(1).apply {
                assertEquals(
                    expected = monster1.run {
                        copy(
                            statusData = statusData.updateName(
                                name = statusData.name + "2"
                            )
                        )
                    },
                    actual = this,
                )
            }

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }
}
