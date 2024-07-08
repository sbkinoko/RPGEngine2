package map.repository.background

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import map.MapModule
import map.domain.BackgroundCell
import map.repository.backgroundcell.BackgroundRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundRepositoryImplTest : KoinTest {

    private val repository: BackgroundRepository by inject()

    private val background = List(3)
    { row ->
        List(3) { col ->
            BackgroundCell(
                cellSize = 10f,
                x = row * 10f,
                y = col * 10f,
            )
        }
    }

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun setTest() {
        runBlocking {
            repository.setBackground(
                background = background
            )

            repository.background.apply {
                assertEquals(
                    expected = background,
                    actual = this,
                )
            }

            repository.getBackgroundAt(0, 0).apply {
                assertEquals(
                    expected = background[0][0],
                    actual = this,
                )
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun checkFlow() {
        runBlocking {
            var count = 0
            val collectJob: Job = launch {
                repository.backgroundFlow.asSharedFlow()
                    .onEach {
                        count++
                    }.launchIn(GlobalScope)
            }

            repository.setBackground(
                background = background
            )
            delay(100)

            assertEquals(
                expected = background,
                actual = repository.backgroundFlow.first(),
            )
            assertEquals(
                expected = 1,
                actual = count
            )

            repository.reload()
            delay(100)

            assertEquals(
                expected = background,
                actual = repository.backgroundFlow.first(),
            )
            assertEquals(
                expected = 2,
                actual = count
            )

            collectJob.cancel()
        }
    }
}
