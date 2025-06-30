package gamescreen.text

import core.menu.IntSelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import gamescreen.text.repository.TextRepository
import org.koin.core.component.inject

class TextViewModel : IntSelectableChildViewModel() {
    val textRepository: TextRepository by inject()

    val showState
        get() = textRepository.textDataStateFlow

    val callBack: () -> Unit
        get() = textRepository.callBack ?: {}

    val text: String
        get() = textRepository.text ?: ""

    override fun goNext() {
        callBack()
        textRepository.pop()
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
