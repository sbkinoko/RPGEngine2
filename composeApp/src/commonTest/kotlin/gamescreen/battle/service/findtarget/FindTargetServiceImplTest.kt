package gamescreen.battle.service.findtarget

import common.status.MonsterStatusTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FindTargetServiceImplTest {
    private lateinit var findTarget: FindTargetService

    @BeforeTest
    fun beforeTest() {
        findTarget = FindTargetServiceImpl()
    }

    @Test
    fun attackTo1When1IsNotActive() {
        val monsters = List(2) {
            if (it == 0) {
                MonsterStatusTest.getNotActiveMonster()
            } else {
                MonsterStatusTest.getMonster()
            }
        }

        val id = 0

        // 対象のモンスターが倒れているときは
        // 隣のモンスター攻撃することを確認
        findTarget.findNext(
            statusList = monsters,
            target = id,
        ).apply {
            assertEquals(
                expected = 1,
                actual = this,
            )
        }
    }

    @Test
    fun attackTo2When2IsNotActive() {
        val monsters = List(2) {
            if (it == 1) {
                MonsterStatusTest.getNotActiveMonster()
            } else {
                MonsterStatusTest.getMonster()
            }
        }

        val id = 1

        // 対象のモンスターが倒れているときは
        // 隣のモンスター攻撃することを確認
        //　ただし、隣が一周して戻ることもある
        findTarget.findNext(
            statusList = monsters,
            target = id,
        ).apply {
            assertEquals(
                expected = 0,
                actual = this,
            )
        }
    }


    @Test
    fun attackTo1When1And2IsNotActive() {
        val activeId = 2
        val monsters = List(3) {
            if (it != 2) {
                MonsterStatusTest.getNotActiveMonster()
            } else {
                MonsterStatusTest.getMonster()
            }
        }

        val id = 0

        // 対象のモンスターが倒れているときは
        // 隣のモンスター攻撃することを確認
        //　ただし、隣が一周して戻ることもある
        findTarget.findNext(
            statusList = monsters,
            target = id,
        ).apply {
            assertEquals(
                expected = activeId,
                actual = this,
            )
        }
    }
}
