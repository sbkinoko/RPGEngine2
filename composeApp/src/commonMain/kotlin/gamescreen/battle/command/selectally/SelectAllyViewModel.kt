package gamescreen.battle.command.selectally

import common.DefaultScope
import core.domain.item.NeedTarget
import core.domain.item.TargetStatusType
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.statusdata.StatusDataRepository
import data.item.skill.SkillRepository
import data.item.tool.ToolRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

// test作る
class SelectAllyViewModel(
    private val statusDataRepository: StatusDataRepository,
) : BattleChildViewModel<Int>() {
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()
    private val actionRepository: ActionRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val toolRepository: ToolRepository by inject()

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        selectManager = SelectManager(
            width = playerNum,
            itemNum = playerNum,
        )
    )

    private val playerId: Int
        get() {
            val command = commandRepository.nowBattleCommandType as SelectAllyCommand
            return command.playerId
        }

    private val _isAllySelecting: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAllySelecting = _isAllySelecting.asStateFlow()

    val targetStatusType: TargetStatusType
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

            return (item as NeedTarget).targetStatusType
        }

    init {
        DefaultScope.launch {
            commandRepository.commandStateFlow.collect {
                _isAllySelecting.value = it is SelectAllyCommand
                if (isAllySelecting.value) {
                    selectCore.select(
                        actionRepository.getAction(playerId).ally
                    )
                }
            }
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is SelectAllyCommand
    }

    override fun selectable(id: Int): Boolean {
        val status = statusDataRepository.getStatusData(id)
        return targetStatusType.canSelect(status)
    }

    override fun goNextImpl() {
        // ターゲットを保存
        actionRepository.setAlly(
            playerId = playerId,
            // 表示ようにリストになっていたが、保存する際は先頭だけ保存して、
            //　実際に攻撃するときに攻撃対象を再計算する
            allyId = selectCore.stateFlow.value,
        )
        changeSelectingActionPlayerUseCase.invoke()
    }
}
