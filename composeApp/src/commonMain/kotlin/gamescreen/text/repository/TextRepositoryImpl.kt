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

    private var textBoxDataList = emptyList<TextBoxData>()

    override val text: String
        get() = nowTextData?.text ?: ""

    override val callBack: () -> Unit
        get() = nowTextData?.callBack ?: {}

    override val needPop: Boolean
        get() = nowTextData?.needPop ?: true

    override fun pop() {
        // これ以上表示するものがなければ空にする
        if (textBoxDataList.isEmpty()) {
            mutableTextDataFlow.value = null
            return
        }

        updateTextData()
    }

    override fun push(textBoxData: TextBoxData) {
        push(
            listOf(textBoxData)
        )
    }

    override fun push(textBoxDataList: List<TextBoxData>) {
        this.textBoxDataList = textBoxDataList
        updateTextData()
    }

    /**
     * 表示内容を次の内容に変更する
     */
    private fun updateTextData() {
        mutableTextDataFlow.value = textBoxDataList.first()
        textBoxDataList = textBoxDataList.drop(1)
    }
}
