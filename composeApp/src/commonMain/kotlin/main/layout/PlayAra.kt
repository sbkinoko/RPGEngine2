package main.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import battle.layout.BattleScreen
import battle.viewmodel.BattleViewModel
import main.domain.ScreenType
import map.layout.MapScreen
import map.viewmodel.MapViewModel
import menu.MenuViewModel
import menu.layout.MenuScreen

@Composable
fun PlayArea(
    screenType: ScreenType,
    screenSize: Int,
    mapViewModel: MapViewModel,
    battleViewModel: BattleViewModel,
    menuViewModel: MenuViewModel,
    modifier: Modifier = Modifier,
) {
    when (screenType) {
        ScreenType.FIELD -> {
            MapScreen(
                modifier = modifier,
                mapViewModel = mapViewModel,
                screenRatio = screenSize / MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
            )
        }

        ScreenType.BATTLE -> {
            BattleScreen(
                modifier = modifier,
                battleViewModel = battleViewModel,
            )
        }

        ScreenType.MENU -> {
            MenuScreen(
                modifier = modifier,
                menuViewModel = menuViewModel,
            )
        }
    }
}
