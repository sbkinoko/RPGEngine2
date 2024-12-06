package gamescreen.choice.repository

import gamescreen.choice.Choice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChoiceRepositoryImpl : ChoiceRepository {
    private val mutableCommandStateFlow: MutableStateFlow<List<Choice>> =
        MutableStateFlow(emptyList())
    override val choiceListStateFlow: StateFlow<List<Choice>> =
        mutableCommandStateFlow.asStateFlow()

    override fun pop() {
        mutableCommandStateFlow.value = emptyList()
    }

    override fun push(commandType: List<Choice>) {
        mutableCommandStateFlow.value = commandType
    }
}
