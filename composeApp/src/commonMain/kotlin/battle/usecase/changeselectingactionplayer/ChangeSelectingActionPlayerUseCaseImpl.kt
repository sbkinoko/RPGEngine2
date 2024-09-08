package battle.usecase.changeselectingactionplayer

import battle.domain.AttackPhaseCommand
import battle.domain.PlayerActionCommand
import battle.domain.PlayerIdCommand
import battle.repository.commandstate.CommandStateRepository
import common.values.playerNum

class ChangeSelectingActionPlayerUseCaseImpl(
    private val commandStateRepository: CommandStateRepository,
) : ChangeSelectingActionPlayerUseCase {

    private val playerId: Int
        get() {
            val command = commandStateRepository.nowCommandType
                    as PlayerIdCommand
            return command.playerId
        }

    override fun invoke() {
        if (playerId < playerNum - 1) {
            commandStateRepository.push(
                PlayerActionCommand(
                    playerId = playerId + 1,
                )
            )
        } else {
            //　一周したので攻撃フェーズに移動
            commandStateRepository.push(AttackPhaseCommand)
        }
    }
}
