package core.domain

import androidx.compose.runtime.Composable
import controller.domain.ControllerCallback
import gamescreen.battle.BattleViewModel
import gamescreen.map.viewmodel.MapViewModel
import gamescreen.menu.MenuViewModel
import org.koin.compose.koinInject

enum class ScreenType {
    BATTLE,
    FIELD,
    MENU,
}

@Composable
fun ScreenType.toViewModel(): ControllerCallback {
    return when (this) {
        ScreenType.BATTLE -> koinInject<BattleViewModel>()
        ScreenType.FIELD -> koinInject<MapViewModel>()
        ScreenType.MENU -> koinInject<MenuViewModel>()
    }
}
