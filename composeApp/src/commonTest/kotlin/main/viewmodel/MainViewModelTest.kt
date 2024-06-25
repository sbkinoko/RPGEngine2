package main.viewmodel

import main.domain.ScreenType
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainViewModelTest : KoinTest {
    private lateinit var mainViewModel: MainViewModel

    @BeforeTest
    fun beforeTest() {
        startKoin {
        }
        mainViewModel = MainViewModel()
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun setField() {
        mainViewModel.toField()

        mainViewModel.nowScreenType.value.apply {
            assertEquals(
                expected = ScreenType.FIELD,
                actual = this,
            )
        }
    }

    @Test
    fun setBattle() {
        mainViewModel.toBattle()

        mainViewModel.nowScreenType.value.apply {
            assertEquals(
                expected = ScreenType.BATTLE,
                actual = this,
            )
        }
    }
}
