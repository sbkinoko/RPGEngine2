package gamescreen.battle.command.selectally

import core.domain.item.HealItem
import core.domain.item.TargetType
import core.repository.player.PlayerStatusRepository
import data.item.skill.SkillRepository
import data.item.tool.ToolRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

class SelectAllyViewModel : BattleChildViewModel() {
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()
    private val actionRepository: ActionRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val toolRepository: ToolRepository by inject()
    private val playerStatusRepository: PlayerStatusRepository by inject()

    private val playerId: Int
        get() {
            val command = commandRepository.nowBattleCommandType as SelectAllyCommand
            return command.playerId
        }

    private val _isAllySelecting: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAllySelecting = _isAllySelecting.asStateFlow()

    val targetType: TargetType
        get() {
            val actionType = actionRepository.getAction(playerId).thisTurnAction

            val item = when (
                actionType
            ) {
                ActionType.Skill -> {
                    val id = actionRepository.getAction(playerId).skillId
                    skillRepository.getItem(id)
                }

                ActionType.TOOL -> {
                    val id = actionRepository.getAction(playerId).toolId
                    toolRepository.getItem(id)
                }

                ActionType.Normal,
                ActionType.None,
                    -> {
                    throw IllegalStateException()
                }
            }

            if (item is HealItem) {
                return item.targetType
            }

            throw RuntimeException("Heal以外でここにいないはず")
        }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            commandRepository.commandStateFlow.collect {
                _isAllySelecting.value = it is SelectAllyCommand
                if (isAllySelecting.value) {
                    selectManager.selected = actionRepository.getAction(playerId).ally
                }
            }
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is SelectAllyCommand
    }

    override fun selectable(): Boolean {
        val id = selectManager.selected
        val status = playerStatusRepository.getStatus(id)
        return targetType.canSelect(status)
    }

    override fun goNextImpl() {
        // ターゲットを保存
        actionRepository.setAlly(
            playerId = playerId,
            // 表示ようにリストになっていたが、保存する際は先頭だけ保存して、
            //　実際に攻撃するときに攻撃対象を再計算する
            allyId = selectManager.selected,
        )
        changeSelectingActionPlayerUseCase.invoke()
    }

    override var selectManager: SelectManager = SelectManager(
        width = playerNum,
        itemNum = playerNum,
    )
}
