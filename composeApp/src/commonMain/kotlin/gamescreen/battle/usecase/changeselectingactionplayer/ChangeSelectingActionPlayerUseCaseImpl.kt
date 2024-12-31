package gamescreen.battle.usecase.changeselectingactionplayer

import gamescreen.battle.domain.AttackPhaseCommand
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.battle.domain.PlayerIdCommand
import gamescreen.battle.repository.commandstate.CommandStateRepository
import values.Constants.Companion.playerNum

class ChangeSelectingActionPlayerUseCaseImpl(
    private val commandStateRepository: CommandStateRepository,
) : ChangeSelectingActionPlayerUseCase {

    private val playerId: Int
        get() {
            val command = commandStateRepository.nowBattleCommandType
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
