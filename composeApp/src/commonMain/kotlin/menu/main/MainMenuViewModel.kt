package menu.main

import common.Timer
import kotlinx.coroutines.flow.SharedFlow
import main.menu.PairedList
import main.menu.SelectableWindowViewModel
import menu.domain.SelectManager
import menu.domain.toMenuType
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
                menuStateRepository.push(it.toMenuType())
            },
        )
    }
    val itemNum: Int = list.size

    val pairedList: List<Pair<MainMenuItem, MainMenuItem?>> =
        PairedList<MainMenuItem>().toPairedList(list)

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
}
