package gamescreen.menu.main

import core.ModuleCore
import gamescreen.menu.ModuleMenu
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

class MainMenuViewModelTest : KoinTest {
    private val viewModel: MainMenuViewModel by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
                ModuleMenu,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkSetSelected() {
        var count = 0
        runBlocking {
            val collectJob = launch {
                viewModel.selectedFlowState.collect {
                    assertEquals(
                        expected = count,
                        actual = it
                    )
                    count++
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
