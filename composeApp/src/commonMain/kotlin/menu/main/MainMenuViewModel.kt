package menu.main

import common.Timer
import common.menu.SelectableWindowViewModel
import kotlinx.coroutines.flow.SharedFlow
import menu.domain.SelectManager
import menu.usecase.backfield.BackFieldUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainMenuViewModel : SelectableWindowViewModel(),
    KoinComponent {
    private val backFieldUseCase: BackFieldUseCase by inject()

    override lateinit var selectManager: SelectManager
    lateinit var selectedFlow: SharedFlow<Int>

    override var timer = Timer(200)

    lateinit var pairedList: MutableList<Pair<MainMenuItem, MainMenuItem?>>
    lateinit var list: List<MainMenuItem>

    private var itemNum = 0

    fun setItems(items: List<MainMenuItem>) {
        itemNum = items.size
        list = items
        pairedList = mutableListOf()
        for (cnt: Int in items.indices step 2) {
            if (cnt + 1 < items.size) {
                //　次の項目とセットで追加
                pairedList.add(Pair(items[cnt], items[cnt + 1]))
            } else {
                // もう項目がないのでnullを入れる
                pairedList.add(Pair(items[cnt], null))
            }
        }
        selectManager = SelectManager(
            width = 2,
            itemNum = itemNum,
        )
        selectedFlow = selectManager.selectedFlow
    }

    override fun pressA() {
        list[selectManager.selected].onClick()
    }

    override fun pressB() {
        backFieldUseCase()
    }

    override fun pressM() {

    }
}
