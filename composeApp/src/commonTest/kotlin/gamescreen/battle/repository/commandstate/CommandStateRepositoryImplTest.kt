package gamescreen.battle.repository.commandstate

import gamescreen.battle.ModuleBattle
import gamescreen.battle.domain.MainCommand
import gamescreen.battle.domain.PlayerActionCommand
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

class CommandStateRepositoryImplTest : KoinTest {
    private val repository: CommandStateRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleBattle,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun initialState() {
        assertEquals(
            expected = CommandStateRepository.INITIAL_COMMAND_STATE,
            actual = repository.nowBattleCommandType
        )
    }

    @Test
    fun pushState() {
        val playerID = 1
        val playerCommand = PlayerActionCommand(
            playerId = playerID,
        )

        runBlocking {
            var count = 0
            val collectJob = launch {
                repository.commandStateFlow.collect {
                    count++
                }
            }

            repository.push(
                playerCommand,
            )

            delay(100)

            assertEquals(
                expected = playerCommand,
                actual = repository.nowBattleCommandType
            )

            assertEquals(
                expected = 1,
                actual = count
            )

            val playerID2 = 2
            val playerCommand2 = PlayerActionCommand(
                playerId = playerID2,
            )

            repository.push(
                playerCommand2,
            )

            delay(100)

            assertEquals(
                expected = playerCommand2,
                actual = repository.nowBattleCommandType
            )

            assertEquals(
                expected = 2,
                actual = count
            )

            collectJob.cancel()
        }
    }

    @Test
    fun popState() {
        runBlocking {
            var count = 0
            val collectJob = launch {
                repository.commandStateFlow.collect {
                    count++
                }
            }

            val playerID = 1
            val playerCommand = PlayerActionCommand(
                playerId = playerID,
            )
            repository.push(
                playerCommand,
            )

            delay(100)

            val playerID2 = 2
            val playerCommand2 = PlayerActionCommand(
                playerId = playerID2,
            )

            repository.push(
                playerCommand2,
            )

            delay(100)

            assertEquals(
                expected = 2,
                actual = count,
            )

            repository.pop()

            delay(100)

            assertEquals(
                expected = 3,
                actual = count,
            )

            assertEquals(
                expected = playerCommand,
                actual = repository.nowBattleCommandType,
            )

            repository.pop()

            delay(100)

            assertEquals(
                expected = 4,
                actual = count
            )

            assertEquals(
                expected = MainCommand,
                actual = repository.nowBattleCommandType,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun checkInit() {
        runBlocking {
            var count = 0
            val collectJob = launch {
                repository.commandStateFlow.collect {
                    count++
                }
            }

            // init前の状態を作成
            val playerID = 1
            val playerCommand = PlayerActionCommand(
                playerId = playerID,
            )
            repository.push(
                playerCommand,
            )
            delay(100)

            val playerID2 = 2
            val playerCommand2 = PlayerActionCommand(
                playerId = playerID2,
            )
            repository.push(
                playerCommand2,
            )
            delay(100)

            assertEquals(
                expected = 2,
                actual = count,
            )

            repository.init()
            delay(100)

            //　initで変化したことを確認
            assertEquals(
                expected = 3,
                actual = count,
            )

            assertEquals(
                expected = MainCommand,
                actual = repository.nowBattleCommandType,
            )

            //　initしたあとはpopしても変化がないことを確認
            repository.pop()

            delay(100)

            assertEquals(
                expected = 3,
                actual = count,
            )

            assertEquals(
                expected = MainCommand,
                actual = repository.nowBattleCommandType,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun popTo() {
        runBlocking {
            var count = 0
            val collectJob = launch {
                repository.commandStateFlow.collect {
                    count++
                }
            }

            //mainのままなのでcountに加算はなし
            repository.init()

            //1人目のアクション
            val first = PlayerActionCommand(
                playerId = 1,
            )
            repository.push(
                first,
            )

            delay(100)

            repository.push(
                PlayerActionCommand(
                    playerId = 2,
                )
            )

            delay(100)
            repository.push(
                PlayerActionCommand(
                    playerId = 3
                )
            )

            delay(100)

            assertEquals(
                expected = 3,
                actual = count,
            )

            repository.popTo {
                it is PlayerActionCommand &&
                        it.playerId == 1
            }

            delay(100)

            assertEquals(
                expected = 4,
                actual = count,
            )

            assertEquals(
                actual = first,
                expected = repository.nowBattleCommandType,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun popToMain() {
        runBlocking {
            var count = 0
            val collectJob = launch {
                repository.commandStateFlow.collect {
                    count++
                }
            }

            //mainのままなのでcountに加算はなし
            repository.init()

            //1人目のアクション
            val first = PlayerActionCommand(
                playerId = 1,
            )
            repository.push(
                first,
            )

            delay(100)

            repository.push(
                PlayerActionCommand(
                    playerId = 2,
                )
            )

            delay(100)
            repository.push(
                PlayerActionCommand(
                    playerId = 3
                )
            )

            delay(100)

            assertEquals(
                expected = 3,
                actual = count,
            )

            repository.popTo {
                it is PlayerActionCommand &&
                        it.playerId == 0
            }

            delay(100)

            assertEquals(
                expected = 4,
                actual = count,
            )

            assertEquals(
                actual = MainCommand,
                expected = repository.nowBattleCommandType,
            )

            collectJob.cancel()
        }
    }
}
