package core.text.repository

import kotlinx.coroutines.flow.MutableSharedFlow

class TextRepositoryImpl : TextRepository {
    override val commandTypeFlow: MutableSharedFlow<Boolean> = MutableSharedFlow(replay = 1)
    override val nowCommandType: Boolean
        get() = flg

    private var flg = false

    private var _text: String = ""

    override fun getText(): String {
        return _text
    }

    override fun setText(text: String) {
        _text = text
    }

    override fun pop() {
        flg = false
        commandTypeFlow.tryEmit(false)
        _text = ""
    }

    override fun push(commandType: Boolean) {
        if (!commandType) {
            throw RuntimeException("Textは必ずtrueを入れる")
        }

        flg = true
        commandTypeFlow.tryEmit(true)
    }
}
