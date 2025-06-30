package gamescreen.menu.main

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
import values.GameParams

class MainMenuViewModel : MenuChildViewModel(),
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()

    private val closeMenuUseCase: CloseMenuUseCase by inject()

    private val moneyRepository: MoneyRepository by inject()
    val moneyStateFlow = moneyRepository.moneyStateFLow

    val list: List<MainMenuItem>
        get() = List(5) {
            val menuType = it.toMenuType()
            if (MenuType.Collision == menuType) {
                MainMenuItem(
                    text = menuType.title + GameParams.showCollisionObject.value,
                )
            } else {
                MainMenuItem(
                    text = menuType.title,
                )
            }
        }

    val itemNum: Int = list.size

    val pairedList: List<Pair<MainMenuItem, MainMenuItem?>>
        get() =
            PairedList<MainMenuItem>().toPairedList(list)

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = itemNum,
    )

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.Main
    }

    override fun goNextImpl() {
        val menuType = selectManager.selected.toMenuType()

        if (menuType == MenuType.Collision) {
            // 当たり判定の項目なら判定を反転する
            GameParams.showCollisionObject.value = GameParams.showCollisionObject.value.not()
            //　次の画面は存在しないので遷移はなし
            return
        }

        menuStateRepository.push(
            menuType = menuType,
        )
    }

    override fun pressB() {
        closeMenuUseCase.invoke()
    }
}
