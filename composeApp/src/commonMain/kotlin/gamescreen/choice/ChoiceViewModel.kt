package gamescreen.choice

import core.menu.SelectableChildViewModel
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class ChoiceViewModel : SelectableChildViewModel<List<Choice>>() {
    val commandRepository: ChoiceRepository by inject()

    private var choiceList: List<Choice> = emptyList()

    val choiceStateFlow: StateFlow<List<Choice>> = commandRepository.choiceListStateFlow

    init {
        CoroutineScope(Dispatchers.Main).launch {
            commandRepository.choiceListStateFlow.collect {
                selectManager = SelectManager(
                    width = 1,
                    itemNum = choiceList.size,
                )
                choiceList = it
            }
        }
    }

    override fun goNext() {
        choiceList[selectManager.selected].callBack()
        commandRepository.pop()
    }

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 2,
    )

    override fun pressB() {
        // 選択肢を選んで欲しいので何もしない
    }
}
