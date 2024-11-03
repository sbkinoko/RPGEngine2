package core.text.repository

import core.domain.TextBoxData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

class TextRepositoryImpl : TextRepository {
    override val commandTypeFlow: MutableSharedFlow<TextBoxData?> = MutableSharedFlow(replay = 1)
    override val commandStateFlow: StateFlow<TextBoxData?>
        get() = TODO("Not yet implemented")

    override val nowCommandType: TextBoxData?
        get() = textBoxData

    private var textBoxData: TextBoxData? = null

    override val text: String
        get() = textBoxData?.text ?: ""

    override val callBack: () -> Unit
        get() = textBoxData?.callBack ?: {}

    override fun pop() {
        commandTypeFlow.tryEmit(null)
        textBoxData = null
    }

    override fun push(commandType: TextBoxData?) {
        textBoxData = commandType
        commandTypeFlow.tryEmit(commandType)
    }
}
