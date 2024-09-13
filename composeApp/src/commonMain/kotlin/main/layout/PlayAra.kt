package main.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import battle.BattleScreen
import main.domain.ScreenType
import map.layout.MapScreen
import map.viewmodel.MapViewModel
import menu.layout.MenuScreen

@Composable
fun PlayArea(
    screenType: ScreenType,
    screenSize: Int,
    modifier: Modifier = Modifier,
) {
    when (screenType) {
        ScreenType.FIELD -> {
            MapScreen(
                modifier = modifier,
                screenRatio = screenSize / MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
            )
        }

        ScreenType.BATTLE -> {
            BattleScreen(
                modifier = modifier,
            )
        }

        ScreenType.MENU -> {
            MenuScreen(
                modifier = modifier,
            )
        }
    }
}
