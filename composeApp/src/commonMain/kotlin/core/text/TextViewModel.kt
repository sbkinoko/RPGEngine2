package core.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import core.domain.TextBoxData
import core.menu.SelectableChildViewModel
import core.text.repository.TextRepository
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class TextViewModel : SelectableChildViewModel<TextBoxData?>() {
    override val commandRepository: TextRepository by inject()

    @Composable
    fun getShowStateAsState(): State<TextBoxData?> {
        return commandRepository.commandTypeFlow.collectAsState(
            null
        )
    }

    override val canBack: Boolean
        get() = false

    override fun isBoundedImpl(commandType: TextBoxData?): Boolean {
        return commandType != null
    }

    val callBack: () -> Unit
        get() = commandRepository.callBack

    val text: String
        get() = commandRepository.text

    override fun goNextImpl() {
        callBack()
        commandRepository.pop()
    }

    // スティック操作に反応しないように1にする
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    // 直接戻りたくないのでAボタンと同じ処理
    override fun pressM() {
        pressA()
    }

    companion object {
        const val INITIAL = 0
    }
}
