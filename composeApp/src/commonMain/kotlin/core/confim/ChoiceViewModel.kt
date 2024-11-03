package core.confim

import core.confim.repository.ChoiceRepository
import core.domain.Choice
import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class ChoiceViewModel : SelectableChildViewModel<List<Choice>>() {
    override val commandRepository: ChoiceRepository by inject()

    private val choiceList
        get() = commandRepository.nowCommandType

    val choiceStateFlow: StateFlow<List<Choice>> = commandRepository.commandStateFlow

    init {
        CoroutineScope(Dispatchers.Default).launch {
            choiceStateFlow.collect {
                println(it)
            }
        }
    }

    override val canBack: Boolean
        get() = false

    override fun goNextImpl() {
        choiceList[selectManager.selected].callBack()
    }

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 2,
    )

    override fun isBoundedImpl(commandType: List<Choice>): Boolean {
        return true
    }

    override fun pressB() {
        // 選択肢を選んで欲しいので何もしない
    }
}
