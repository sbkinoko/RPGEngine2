package manager.battle

import domain.common.status.MonsterStatusTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FindTargetTest {
    private lateinit var findTarget: FindTarget

    @BeforeTest
    fun beforeTest() {
        findTarget = FindTarget()
    }

    @Test
    fun attackWhenIsActive() {
        val monsters = List(2) {
            MonsterStatusTest.getMonster()
        }

        for (cnt: Int in 0..1) {
            // 対象のモンスターが戦闘可能な場合
            //　対象のモンスターを攻撃することを確認
            findTarget.find(
                monsters = monsters,
                target = cnt,
            ).apply {
                assertEquals(
                    expected = cnt,
                    actual = this,
                )
            }
        }
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
        findTarget.find(
            monsters = monsters,
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
        findTarget.find(
            monsters = monsters,
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
        findTarget.find(
            monsters = monsters,
            target = id,
        ).apply {
            assertEquals(
                expected = activeId,
                actual = this,
            )
        }
    }
}
