package main.viewmodel

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class MainViewModelTest : KoinTest {

    @BeforeTest
    fun beforeTest() {
        startKoin {
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
