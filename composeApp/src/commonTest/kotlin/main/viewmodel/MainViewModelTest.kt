package main.viewmodel

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

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

}
