package menu.main

import common.Timer
import common.menu.SelectableWindowViewModel
import kotlinx.coroutines.flow.SharedFlow
import menu.domain.SelectManager
import menu.layout.toMenuType
import menu.repository.menustate.MenuStateRepository
import menu.usecase.backfield.BackFieldUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainMenuViewModel : SelectableWindowViewModel(),
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()

    private val backFieldUseCase: BackFieldUseCase by inject()

    override var timer = Timer(200)

    val list: List<MainMenuItem> = List(5) {
        MainMenuItem(
            text = it.toMenuType().title,
            onClick = {
                menuStateRepository.menuType = it.toMenuType()
            },
        )
    }
    val itemNum: Int = list.size

    val pairedList: List<Pair<MainMenuItem, MainMenuItem?>> = list.toPairedList()

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = itemNum,
    )
    var selectedFlow: SharedFlow<Int> = selectManager.selectedFlow

    override fun pressA() {
        list[selectManager.selected].onClick()
    }

    override fun pressB() {
        backFieldUseCase()
    }

    override fun pressM() {

    }
}

fun List<MainMenuItem>.toPairedList(): List<Pair<MainMenuItem, MainMenuItem?>> {
    val pairedList: MutableList<Pair<MainMenuItem, MainMenuItem?>> = mutableListOf()
    for (cnt: Int in this.indices step 2) {
        if (cnt + 1 < this.size) {
            //　次の項目とセットで追加
            pairedList.add(Pair(this[cnt], this[cnt + 1]))
        } else {
            // もう項目がないのでnullを入れる
            pairedList.add(Pair(this[cnt], null))
        }
    }
    return pairedList.toList()
}
