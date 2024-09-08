package battle.usecase.changeselectingactionplayer

import battle.BattleModule
import battle.domain.AttackPhaseCommand
import battle.domain.PlayerActionCommand
import battle.domain.PlayerIDCommand
import battle.repository.commandstate.CommandStateRepository
import common.values.playerNum
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChangeSelectingActionPlayerUseCaseImplTest : KoinTest {
    private val useCase: ChangeSelectingActionPlayerUseCase by inject()

    private val commandStateRepository: CommandStateRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(BattleModule)
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * 最後以外のプレイヤーの場合、次のプレイヤーの
     * 行動選択に移行する
     */
    @Test
    fun changePlayer() {
        val first = 0
        val next = 1

        commandStateRepository.init()
        commandStateRepository.push(
            PlayerActionCommand(
                playerId = first,
            )
        )

        useCase()

        assertTrue {
            commandStateRepository.nowCommandType is PlayerActionCommand
        }

        assertEquals(
            expected = next,
            actual = (commandStateRepository.nowCommandType as PlayerIDCommand).playerId
        )
    }

    /**
     * 最後のプレイヤーの場合、攻撃フェーズに移行する
     */
    @Test
    fun changePhase() {
        // 最後のプレイヤー
        val first = playerNum - 1
        commandStateRepository.init()
        commandStateRepository.push(
            PlayerActionCommand(
                playerId = first,
            )
        )

        useCase()

        assertEquals(
            expected = AttackPhaseCommand,
            actual = commandStateRepository.nowCommandType
        )
    }
}
