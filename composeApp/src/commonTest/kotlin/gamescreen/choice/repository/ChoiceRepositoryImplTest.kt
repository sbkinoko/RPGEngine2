package gamescreen.choice.repository

import gamescreen.choice.Choice
import gamescreen.choice.ChoiceModule
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

class ChoiceRepositoryImplTest : KoinTest {
    private val repository: ChoiceRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ChoiceModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * 初期状態は空であることを確認
     */
    @Test
    fun initialState() {
        assertEquals(
            expected = listOf(),
            actual = repository.choiceListStateFlow.value
        )
    }

    /**
     * 入力した値に更新されているか確認
     * flowが流れているか確認
     */
    @Test
    fun pushState() {
        runBlocking {
            val list = listOf(
                Choice(
                    text = "",
                    callBack = {}
                )
            )

            var count = 0
            val collectJob = launch {
                repository.choiceListStateFlow.collect {
                    count++

                    assertEquals(
                        expected = list,
                        actual = it,
                    )
                }
            }

            repository.push(list)

            delay(100)

            assertEquals(
                expected = 1,
                actual = count
            )

            collectJob.cancel()
        }
    }
}
