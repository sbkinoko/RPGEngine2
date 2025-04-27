package gamescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.battle.BattleScreen
import gamescreen.choice.ChoiceWindow
import gamescreen.map.layout.MapScreen
import gamescreen.map.viewmodel.MapViewModel
import gamescreen.menu.MenuScreen
import gamescreen.menushop.ShopMenu
import gamescreen.text.TextWindow
import values.Colors

@Composable
fun PlayArea(
    gameScreenType: GameScreenType,
    screenSize: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (gameScreenType) {
            GameScreenType.FIELD -> {
                MapScreen(
                    modifier = Modifier.fillMaxSize(),
                    screenRatio = screenSize / MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
                )
            }

            GameScreenType.BATTLE -> {
                BattleScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            GameScreenType.MENU -> {
                MenuScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        ShopMenu(
            modifier = Modifier.fillMaxSize(),
        )

        TextWindow(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Colors.OverlayMenu
                ),
        )

        ChoiceWindow(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Colors.OverlayMenu
                ),
        )
    }
}
