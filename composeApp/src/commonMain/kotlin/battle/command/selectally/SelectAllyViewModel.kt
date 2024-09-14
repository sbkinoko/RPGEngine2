package battle.command.selectally

import battle.BattleChildViewModel
import battle.domain.CommandType
import battle.domain.SelectAllyCommand
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import common.values.playerNum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import menu.domain.SelectManager
import org.koin.core.component.inject

class SelectAllyViewModel : BattleChildViewModel() {
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    private val _isAllySelecting: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAllySelecting = _isAllySelecting.asStateFlow()

    override val canBack: Boolean
        get() = true

    init {
        CoroutineScope(Dispatchers.Default).launch {
            commandStateRepository.commandTypeFlow.collect {
                _isAllySelecting.value = it is SelectAllyCommand
            }
        }
    }

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is SelectAllyCommand
    }

    override fun goNextImpl() {
        changeSelectingActionPlayerUseCase.invoke()
    }

    override var selectManager: SelectManager = SelectManager(
        width = playerNum,
        itemNum = playerNum,
    )
}
