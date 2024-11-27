package gamescreen.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import gamescreen.text.repository.TextRepository
import org.koin.core.component.inject

class TextViewModel : SelectableChildViewModel<TextBoxData?>() {
    val commandRepository: TextRepository by inject()

    // fixme stateFlowを返すようにする
    @Composable
    fun getShowStateAsState(): State<TextBoxData?> {
        return commandRepository.commandTypeFlow.collectAsState(
            null
        )
    }

    val callBack: () -> Unit
        get() = commandRepository.callBack

    val text: String
        get() = commandRepository.text

    override fun goNext() {
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

    // 直接戻りたくないのでAボタンと同じ処理
    override fun pressB() {
        pressA()
    }

    companion object {
        const val INITIAL = 0
    }
}
