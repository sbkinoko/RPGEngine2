package menu.main

import common.Timer
import core.menu.PairedList
import kotlinx.coroutines.flow.SharedFlow
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.domain.toMenuType
import menu.repository.menustate.MenuStateRepository
import menu.usecase.backfield.BackFieldUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainMenuViewModel : MenuChildViewModel(),
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()

    private val backFieldUseCase: BackFieldUseCase by inject()
    override val canBack: Boolean
        get() = true

    override var timer = Timer(200)
    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.Main
    }

    override fun goNextImpl() {
        menuStateRepository.push(
            selectManager.selected.toMenuType()
        )
    }

    val list: List<MainMenuItem> = List(5) {
        MainMenuItem(
            text = it.toMenuType().title,
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

    override fun pressB() {
        backFieldUseCase()
    }
}
