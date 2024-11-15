package core.confim

import core.confim.repository.ChoiceRepository
import core.domain.Choice
import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject

class ChoiceViewModel : SelectableChildViewModel<List<Choice>>() {
    override val commandRepository: ChoiceRepository by inject()

    private val choiceList
        get() = commandRepository.nowCommandType

    val choiceStateFlow: StateFlow<List<Choice>> = commandRepository.commandStateFlow

    override fun goNextImpl() {
        choiceList[selectManager.selected].callBack()
        commandRepository.pop()
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
