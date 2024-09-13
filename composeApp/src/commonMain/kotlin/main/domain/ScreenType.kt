package main.domain

import androidx.compose.runtime.Composable
import battle.BattleViewModel
import controller.domain.ControllerCallback
import map.viewmodel.MapViewModel
import menu.MenuViewModel
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
