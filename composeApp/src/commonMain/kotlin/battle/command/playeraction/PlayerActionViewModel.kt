package battle.command.playeraction

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.CommandType
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.action.ActionRepository
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import common.repository.player.PlayerRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class PlayerActionViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()

    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    val normalAttack = 0
    val skill = 1

    val playerId: Int
        get() = (commandStateRepository.nowCommandType as PlayerActionCommand).playerId

    fun init() {
        // プレイヤーが行動不能なら次のキャラに移動する
        if (playerRepository.getPlayer(playerId).isActive.not()) {
            changeSelectingActionPlayerUseCase.invoke()
            return
        }

        selectManager.selected = when (
            actionRepository.getAction(playerId = playerId).thisTurnAction
        ) {
            ActionType.Normal -> normalAttack
            ActionType.Skill -> skill
        }
    }

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is PlayerActionCommand
    }

    override fun goNextImpl() {
        when (selectManager.selected) {
            normalAttack -> {
                // 行動を保存
                actionRepository.setAction(
                    actionType = ActionType.Normal,
                    playerId = playerId,
                )

                // 画面変更
                commandStateRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            skill -> commandStateRepository.push(
                SkillCommand(playerId),
            )
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
