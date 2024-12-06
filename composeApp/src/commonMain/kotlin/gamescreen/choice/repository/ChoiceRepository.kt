package gamescreen.choice.repository

import gamescreen.choice.Choice
import kotlinx.coroutines.flow.StateFlow

interface ChoiceRepository {
    val choiceListStateFlow: StateFlow<List<Choice>>

    fun push(commandType: List<Choice>)

    fun pop()
}
