package gamescreen.battle.command.main

import common.DefaultScope
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.command.OnClick2
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.MainCommand
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BattleMainViewModel :
    BattleChildViewModel(),
    OnClick2<MainCommandType> {

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is MainCommand
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = itemNum,
    )

    override val entries: List<MainCommandType>
        get() = MainCommandType.entries

    private val mutable = MutableStateFlow(selectedFlowState.value.toEnum())
    override val selectedFlowState2: StateFlow<MainCommandType>
        get() = mutable.asStateFlow()

    init {
        DefaultScope.launch {
            selectedFlowState.collect {
                mutable.value = it.toEnum()
            }
        }
    }

    override fun goNextImpl() {
        when (selectManager.selected.toEnum()) {
            MainCommandType.Battle -> commandRepository.push(
                PlayerActionCommand(
                    playerId = 0,
                )
            )

            MainCommandType.Escape -> commandRepository.push(
                EscapeCommand
            )
        }
    }

    override fun onClickItem(id: MainCommandType) {
        onClickItem(id.toInt())
    }
}
