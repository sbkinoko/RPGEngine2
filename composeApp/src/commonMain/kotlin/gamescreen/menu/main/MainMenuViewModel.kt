package gamescreen.menu.main

import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.memory.money.MoneyRepository
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

    val list: List<MainMenuItem>
        get() = List(5) {
            val menuType = it.toMenuType()
            MainMenuItem(
                text = menuType.title,
            )
        }

    val itemNum: Int = list.size

    val chunkSize = 2
    val chunkedList: List<List<MainMenuItem>>
        get() = list.chunked(chunkSize)

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 2,
            itemNum = itemNum,
        )
    )


    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.Main
    }

    override fun goNextImpl() {
        val menuType = selectCore.stateFlow.value.toMenuType()

        menuStateRepository.push(
            menuType = menuType,
        )
    }

    override fun pressB() {
        closeMenuUseCase.invoke()
    }
}
