package gamescreen.text

import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import gamescreen.text.repository.TextRepository
import org.koin.core.component.inject

class TextViewModel : SelectableChildViewModel<Int>() {
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

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = 1,
        )
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
