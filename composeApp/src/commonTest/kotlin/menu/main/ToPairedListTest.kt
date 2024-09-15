package menu.main

import kotlin.test.Test
import kotlin.test.assertEquals

class ToPairedListTest {

    @Test
    fun checkSetPairEven() {
        val list = List(2) {
            MainMenuItem(
                text = it.toString(),
                onClick = {}
            )
        }
        list.toPairedList().forEachIndexed { index, pair ->
            assertEquals(
                expected = list[index * 2],
                actual = pair.first
            )

            assertEquals(
                expected = list[index * 2 + 1],
                actual = pair.second,
            )
        }
    }

    @Test
    fun checkSetPairOdd() {
        val list = List(3) {
            MainMenuItem(
                text = it.toString(),
                onClick = {}
            )
        }

        list.toPairedList().forEachIndexed { index, pair ->
            if (index == 0) {
                assertEquals(
                    expected = list[index * 2],
                    actual = pair.first
                )

                assertEquals(
                    expected = list[index * 2 + 1],
                    actual = pair.second,
                )
            } else {
                assertEquals(
                    expected = list[index * 2],
                    actual = pair.first
                )
                assertEquals(
                    expected = null,
                    actual = pair.second
                )
            }
        }
    }
}
