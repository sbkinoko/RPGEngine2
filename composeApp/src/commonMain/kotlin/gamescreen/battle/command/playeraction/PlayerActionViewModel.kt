package gamescreen.battle.command.playeraction

import common.DefaultScope
import core.repository.statusdata.StatusDataRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.command.OnClick2
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.battle.domain.PlayerIdCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.domain.SkillCommand
import gamescreen.battle.domain.ToolCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

// TODO: test作る 
class PlayerActionViewModel(
    private val statusDataRepository: StatusDataRepository,
) : BattleChildViewModel(),
    OnClick2<PlayerActionCommandType> {
    private val actionRepository: ActionRepository by inject()

    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = itemNum,
    )

    override val entries: List<PlayerActionCommandType>
        get() = PlayerActionCommandType.entries

    private val mutable = MutableStateFlow(selectedFlowState.value.toEnum())

    override val selectedFlowState2: StateFlow<PlayerActionCommandType>
        get() = mutable.asStateFlow()


    val playerId: Int
        get() = (commandRepository.nowBattleCommandType as PlayerActionCommand).playerId

    init {
        DefaultScope.launch {
            selectedFlowState.collect {
                mutable.value = it.toEnum()
            }
        }
    }

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

        selectManager.selected = when (action) {
            ActionType.Normal -> PlayerActionCommandType.Normal.toInt()
            ActionType.Skill -> PlayerActionCommandType.Skill.toInt()
            ActionType.TOOL -> PlayerActionCommandType.Tool.toInt()
            ActionType.None -> throw RuntimeException()
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is PlayerActionCommand
    }

    override fun goNextImpl() {
        when (selectedFlowState2.value) {
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

    override fun onClickItem(id: PlayerActionCommandType) {
        onClickItem(id.toInt())
    }
}
