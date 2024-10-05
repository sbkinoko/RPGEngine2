package menu.main

import gamescreen.menu.main.MainMenuViewModel
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
}
