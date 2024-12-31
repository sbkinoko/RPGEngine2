package gamescreen.text.repository

import gamescreen.text.TextBoxData
import kotlinx.coroutines.flow.StateFlow

interface TextRepository {
    val callBack: () -> Unit
    val text: String

    val textDataStateFlow: StateFlow<TextBoxData?>
    val nowTextData: TextBoxData?

    fun push(textBoxData: TextBoxData?)

    fun pop()
}
