package gamescreen.battle.repository.battlemonster

import core.domain.status.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.battlemonster.BattleMonsterRepositoryImpl
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

// todo　coroutine.testを利用してテストするようにする
//@OptIn(ExperimentalCoroutinesApi::class)
class BattleMonsterRepositoryImplTest {
    private lateinit var battleMonsterRepository: BattleMonsterRepository
//    private val testDispatcher = StandardTestDispatcher()

    private val monster1 = MonsterStatus(
        imgId = 1,
        name = "monster1",
        hp = HP(
            maxValue = 10,
        ),
        mp = MP(
            maxValue = 10,
        )
    )
    private val monster2 = MonsterStatus(
        imgId = 1,
        name = "monster1",
        hp = HP(
            maxValue = 10,
        ),
        mp = MP(
            maxValue = 10,
        )
    )
    private val monsterList = listOf(
        monster1,
        monster2,
    )

    @BeforeTest
    fun beforeTest() {
        battleMonsterRepository = BattleMonsterRepositoryImpl()
//        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun afterTest() {
//        Dispatchers.resetMain()
    }

    @Test
    fun setMonsterTest() {
        // = runTest {
        runBlocking {
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
        }
    }

//    @Test
//    fun checkFlow() {
//        val collectValue: MutableList<MutableList<MonsterStatus>> = mutableListOf()
//
//        var collectJob: Job = Job()
//        CoroutineScope(Dispatchers.IO).launch {
//            collectJob = launch {
//                battleMonsterRepository.monsterListFlow.collect {
//                    collectValue.add(it.toMutableList())
//                }
//            }
//        }
//
//        runBlocking {
//            battleMonsterRepository.setMonster(
//                monsters = monsterList.toMutableList(),
//            )
//
//            battleMonsterRepository.setMonster(
//                monsters = monsterList.toMutableList(),
//            )
//
//            collectJob.cancel()
//
//            assertEquals(
//                expected = 1,
//                actual = collectValue.size
//            )
//            assertEquals(
//                expected = monsterList,
//                actual = collectValue[0],
//            )
//        }
//    }
}
