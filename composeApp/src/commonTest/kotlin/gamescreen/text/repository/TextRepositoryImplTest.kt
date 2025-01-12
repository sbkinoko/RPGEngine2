package gamescreen.text.repository

import gamescreen.text.ModuleText
import gamescreen.text.TextBoxData
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

class TextRepositoryImplTest : KoinTest {
    private val textRepository: TextRepository by inject()

    private val callBack1 = {}
    private val text1 = "test"
    private val textBoxData1 = TextBoxData(
        text = text1,
        callBack = callBack1,
    )
    private val callBack2 = {}
    private val text2 = "test2"
    private val textBoxData2 = TextBoxData(
        text = text2,
        callBack = callBack2,
    )

    private val textBoxDataList = listOf(
        textBoxData1,
        textBoxData2,
    )

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleText,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * 初期値のテスト
     */
    @Test
    fun testInit() {
        assertEquals(
            expected = null,
            actual = textRepository.callBack,
        )

        assertEquals(
            expected = null,
            actual = textRepository.text,
        )
    }

    /**
     * 1つの要素をpushしたときのテスト
     */
    @Test
    fun push1Data() {
        var count = 0

        runBlocking {
            val collectJob = launch {
                textRepository.textDataStateFlow.collect {
                    count++
                }
            }
            delay(50)

            textRepository.push(
                textBoxData = textBoxData1,
            )

            delay(50)

            assertEquals(
                expected = callBack1,
                actual = textRepository.callBack,
            )

            assertEquals(
                expected = text1,
                actual = textRepository.text,
            )

            assertEquals(
                expected = 2,
                actual = count,
            )

            collectJob.cancel()

        }
    }

    /**
     * 1つの要素をpopしたときのテスト
     */
    @Test
    fun pop1Data() {
        var count = 0

        runBlocking {
            val collectJob = launch {
                textRepository.textDataStateFlow.collect {
                    count++
                }
            }

            delay(50)

            textRepository.push(
                textBoxData = textBoxData1,
            )

            delay(50)

            textRepository.pop()

            delay(50)

            assertEquals(
                expected = null,
                actual = textRepository.callBack,
            )

            assertEquals(
                expected = null,
                actual = textRepository.text,
            )

            assertEquals(
                expected = 3,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * 長さ2のリストをpushしたときのテスト
     */
    @Test
    fun push2Data() {
        var count = 0

        runBlocking {
            val collectJob = launch {
                textRepository.textDataStateFlow.collect {
                    count++
                }
            }

            delay(50)

            textRepository.push(
                textBoxDataList = textBoxDataList,
            )

            delay(50)

            assertEquals(
                expected = callBack1,
                actual = textRepository.callBack,
            )

            assertEquals(
                expected = text1,
                actual = textRepository.text,
            )

            assertEquals(
                expected = 2,
                actual = count,
            )

            collectJob.cancel()

        }
    }

    /**
     * 長さ2のリストで1回popしたときのテスト
     */
    @Test
    fun pop2DataOnce() {
        var count = 0

        runBlocking {
            val collectJob = launch {
                textRepository.textDataStateFlow.collect {
                    count++
                }
            }

            delay(50)

            textRepository.push(
                textBoxDataList = textBoxDataList,
            )

            delay(50)

            textRepository.pop()

            delay(50)

            assertEquals(
                expected = callBack2,
                actual = textRepository.callBack,
            )

            assertEquals(
                expected = text2,
                actual = textRepository.text,
            )

            assertEquals(
                expected = 3,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * 長さ2のリストで2回popしたときのテスト
     */
    @Test
    fun pop2DataTwice() {
        var count = 0

        runBlocking {
            val collectJob = launch {
                textRepository.textDataStateFlow.collect {
                    count++
                }
            }

            delay(50)

            textRepository.push(
                textBoxDataList = textBoxDataList,
            )

            delay(50)

            textRepository.pop()

            delay(50)

            textRepository.pop()

            delay(50)

            assertEquals(
                expected = null,
                actual = textRepository.callBack,
            )

            assertEquals(
                expected = null,
                actual = textRepository.text,
            )

            assertEquals(
                expected = 4,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun popEmpty() {
        var count = 0

        runBlocking {
            val collectJob = launch {
                textRepository.textDataStateFlow.collect {
                    count++
                }
            }

            delay(50)

            // 初期データが流れることを確認
            assertEquals(
                expected = 1,
                actual = count,
            )

            textRepository.pop()

            delay(50)

            // データの更新がないことを確認
            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }
}
