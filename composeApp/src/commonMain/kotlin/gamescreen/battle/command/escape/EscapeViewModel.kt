package gamescreen.battle.command.escape

import common.DefaultScope
import core.domain.BattleResult
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.command.OnClick2
import gamescreen.battle.command.escape.EscapeCommandType.BackToBattle
import gamescreen.battle.command.escape.EscapeCommandType.Escape
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.FinishCommand
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EscapeViewModel
    : BattleChildViewModel(),
    OnClick2<EscapeCommandType> {

    override val entries: List<EscapeCommandType>
        get() = EscapeCommandType.entries

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = itemNum,
    )

    private val mutable = MutableStateFlow(
        selectedFlowState.value.toEnum()
    )
    override val selectedFlowState2: StateFlow<EscapeCommandType> = mutable.asStateFlow()

    init {
        DefaultScope.launch {
            selectedFlowState.collect {
                mutable.value = it.toEnum()
            }
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType == EscapeCommand
    }

    override fun onClickItem(
        id: EscapeCommandType,
    ) {
        onClickItem(id.toInt())
    }

    override fun goNextImpl() {
        when (selectedFlowState2.value) {
            Escape -> this.commandRepository.push(
                FinishCommand(
                    battleResult = BattleResult.Escape,
                )
            )

            BackToBattle -> commandRepository.pop()
        }
    }
}
