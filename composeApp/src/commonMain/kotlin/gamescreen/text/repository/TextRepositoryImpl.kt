package gamescreen.text.repository

import gamescreen.text.TextBoxData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TextRepositoryImpl : TextRepository {
    private val mutableTextDataFlow =
        MutableStateFlow<TextBoxData?>(null)
    override val textDataStateFlow: StateFlow<TextBoxData?>
        get() = mutableTextDataFlow.asStateFlow()

    override val nowTextData: TextBoxData?
        get() = textDataStateFlow.value


    override val text: String
        get() = nowTextData?.text ?: ""

    override val callBack: () -> Unit
        get() = nowTextData?.callBack ?: {}

    override fun pop() {
        mutableTextDataFlow.value = null
    }

    override fun push(textBoxData: TextBoxData?) {
        mutableTextDataFlow.value = textBoxData
    }
}
