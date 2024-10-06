package gamescreen.battle.command.playeraction

import core.repository.player.PlayerRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.battle.domain.PlayerIdCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.domain.SkillCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class PlayerActionViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()

    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 3,
    )

    val normalAttack = 0
    val skill = 1
    val tool = 2

    val playerId: Int
        get() = (commandRepository.nowCommandType as PlayerActionCommand).playerId

    fun init() {
        // プレイヤーが行動不能なら次のキャラに移動する
        if (playerRepository.getStatus(playerId).isActive.not()) {
            actionRepository.setAction(
                playerId = playerId,
                actionType = ActionType.None,
            )
            changeSelectingActionPlayerUseCase.invoke()
            return
        }

        val action = actionRepository.getLastSelectAction(playerId = playerId)

        selectManager.selected = when (action) {
            ActionType.Normal -> normalAttack
            ActionType.Skill -> skill
            ActionType.None -> throw RuntimeException()
        }
    }

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
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
                commandRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            skill -> commandRepository.push(
                SkillCommand(playerId),
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
            val player = playerRepository.getStatus(playerId)

            player.isActive
        }
    }
}
