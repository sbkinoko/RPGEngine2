package gamescreen.battle.command.playeraction

import core.menu.SelectCore
import core.menu.SelectCoreEnum
import core.repository.character.statusdata.StatusDataRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.battle.domain.PlayerIdCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.domain.SkillCommand
import gamescreen.battle.domain.ToolCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import org.koin.core.component.inject

// TODO: test作る 
class PlayerActionViewModel(
    private val statusDataRepository: StatusDataRepository,
) : BattleChildViewModel<PlayerActionCommandType>() {
    private val actionRepository: ActionRepository by inject()

    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    override var selectCore: SelectCore<PlayerActionCommandType> = SelectCoreEnum(
        entries = PlayerActionCommandType.entries
    )

    val playerId: Int
        get() = (commandRepository.nowBattleCommandType as PlayerActionCommand).playerId

    fun init() {
        // プレイヤーが行動不能なら次のキャラに移動する
        if (statusDataRepository.getStatusData(playerId).isActive.not()) {
            actionRepository.setAction(
                playerId = playerId,
                actionType = ActionType.None,
            )
            changeSelectingActionPlayerUseCase.invoke()
            return
        }

        val action = actionRepository.getLastSelectAction(playerId = playerId)

        selectCore.select(
            when (action) {
                ActionType.Normal -> PlayerActionCommandType.Normal
                ActionType.Skill -> PlayerActionCommandType.Skill
                ActionType.TOOL -> PlayerActionCommandType.Tool
                ActionType.None -> throw RuntimeException()
            }
        )
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is PlayerActionCommand
    }

    override fun goNextImpl() {
        when (selectCore.stateFlow.value) {
            PlayerActionCommandType.Normal -> {
                // 行動を保存
                actionRepository.setAction(
                    actionType = ActionType.Normal,
                    playerId = playerId,
                )

                // 画面変更
                commandRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            PlayerActionCommandType.Skill -> commandRepository.push(
                SkillCommand(playerId),
            )

            PlayerActionCommandType.Tool -> commandRepository.push(
                ToolCommand(playerId)
            )
        }
    }

    override fun pressB() {
        // アクティブなプレイヤーまで戻る
        commandRepository.popTo {
            // playerActionじゃなければダメ
            val command: PlayerIdCommand = it as? PlayerIdCommand
                ?: return@popTo false

            val playerId = command.playerId
            val player = statusDataRepository.getStatusData(playerId)

            player.isActive
        }
    }
}
