package main.viewmodel

import main.domain.ScreenType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel

    @BeforeTest
    fun beforeTest() {
        mainViewModel = MainViewModel()
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
