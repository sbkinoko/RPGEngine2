package gamescreen.menu.main

import common.Timer
import core.menu.PairedList
import core.repository.money.MoneyRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.domain.toMenuType
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.menu.usecase.backfield.CloseMenuUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainMenuViewModel : MenuChildViewModel(),
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()

    private val closeMenuUseCase: CloseMenuUseCase by inject()

    private val moneyRepository: MoneyRepository by inject()
    val moneyStateFlow = moneyRepository.moneyStateFLow

    override var timer = Timer(200)

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

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.Main
    }

    override fun goNextImpl() {
        menuStateRepository.push(
            selectManager.selected.toMenuType()
        )
    }

    override fun pressB() {
        closeMenuUseCase.invoke()
    }
}
