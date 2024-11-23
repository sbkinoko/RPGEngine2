package gamescreen.map.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class VelocityToDirTest {

    /**
     * 速度が0のときの挙動を確認
     */
    @Test
    fun checkStop() {
        val velocity = Velocity(
            x = 0f,
            y = 0f,
        )
        val dir = velocity.toDir()
        assertEquals(
            expected = PlayerDir.NONE,
            actual = dir,
        )
    }

    /**
     * 上方向を向いていることを確認
     */
    @Test
    fun checkUp() {
        val velocity = Velocity(
            x = 0.1f,
            y = -1f,
        )

        val dir = velocity.toDir()

        assertEquals(
            expected = PlayerDir.UP,
            actual = dir,
        )
    }

    /**
     * 下方向を向いていることを確認
     */
    @Test
    fun checkDown() {
        val velocity = Velocity(
            x = 0.1f,
            y = 1f,
        )

        val dir = velocity.toDir()

        assertEquals(
            expected = PlayerDir.DOWN,
            actual = dir,
        )
    }

    /**
     * 左方向を向いていることを確認
     */
    @Test
    fun checkLeft() {
        val velocity = Velocity(
            x = -1f,
            y = 0.1f,
        )
        val dir = velocity.toDir()
        assertEquals(
            expected = PlayerDir.LEFT,
            actual = dir,
        )
    }

    /**
     * 右方向を向いていることを確認
     */
    @Test
    fun checkRight() {
        val velocity = Velocity(
            x = 1f,
            y = 0.1f,
        )
        val dir = velocity.toDir()
        assertEquals(
            expected = PlayerDir.RIGHT,
            actual = dir,
        )
    }


    /**
     * 右上方向を向いているときの挙動を確認
     */
    @Test
    fun checkRightUp() {
        val velocity = Velocity(
            x = 1f,
            y = -1f,
        )
        val dir = velocity.toDir()
        assertEquals(
            expected = PlayerDir.UP,
            actual = dir,
        )
    }

    /**
     * 左上方向を向いているときの挙動を確認
     */
    @Test
    fun checkLeftUp() {
        val velocity = Velocity(
            x = -1f,
            y = -1f,
        )
        val dir = velocity.toDir()
        assertEquals(
            expected = PlayerDir.UP,
            actual = dir,
        )
    }

    /**
     * 右下方向を向いているときの挙動を確認
     */
    @Test
    fun checkRightDown() {
        val velocity = Velocity(
            x = 1f,
            y = 1f,
        )
        val dir = velocity.toDir()
        assertEquals(
            expected = PlayerDir.DOWN,
            actual = dir,
        )
    }

    /**
     * 左下方向を向いているときの挙動を確認
     */
    @Test
    fun checkLeftDown() {
        val velocity = Velocity(
            x = -1f,
            y = 1f,
        )
        val dir = velocity.toDir()
        assertEquals(
            expected = PlayerDir.DOWN,
            actual = dir,
        )
    }
}
