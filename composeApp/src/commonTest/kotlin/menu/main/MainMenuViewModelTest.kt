package menu.main

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainMenuViewModelTest {
    private lateinit var viewModel: MainMenuViewModel

    @BeforeTest
    fun beforeTest() {
        viewModel = MainMenuViewModel()
    }

    @Test
    fun checkSetSelected() {
        val list = List(3) {
            MainMenuItem(
                text = it.toString(),
                onClick = {}
            )
        }
        viewModel.setItems(
            list
        )

        var count = 0
        runBlocking {
            val collectJob = launch {
                viewModel.selectedFlow.collect {
                    count++
                    assertEquals(
                        expected = count,
                        actual = it
                    )
                }
            }
            delay(100)

            viewModel.setSelected(1)
            delay(100)

            viewModel.setSelected(2)
            delay(100)

            collectJob.cancel()
        }
    }

    @Test
    fun checkSetPairEven() {
        val list = List(2) {
            MainMenuItem(
                text = it.toString(),
                onClick = {}
            )
        }
        viewModel.setItems(
            list
        )
        viewModel.pairedList.forEachIndexed { index, pair ->
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
        viewModel.setItems(
            list
        )
        viewModel.pairedList.forEachIndexed { index, pair ->
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
