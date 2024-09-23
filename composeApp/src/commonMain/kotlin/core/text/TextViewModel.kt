package core.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import core.text.repository.TextRepository
import main.menu.SelectableChildViewModel
import menu.domain.SelectManager
import org.koin.core.component.inject

class TextViewModel : SelectableChildViewModel<Boolean>() {
    override val commandRepository: TextRepository by inject()

    @Composable
    fun getShowStateAsState(): State<Boolean> {
        return commandRepository.commandTypeFlow.collectAsState(
            false
        )
    }

    override val canBack: Boolean
        get() = false

    var callBack: () -> Unit = {}

    val text: String
        get() = "回復した"

    override fun goNextImpl() {
        callBack()
        commandRepository.pop()
    }

    // スティック操作に反応しないように1にする
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    override fun isBoundedImpl(commandType: Boolean): Boolean {
        return commandType
    }

    // 直接戻りたくないのでAボタンと同じ処理
    override fun pressM() {
        pressA()
    }

    companion object {
        const val INITIAL = 0
    }
}
