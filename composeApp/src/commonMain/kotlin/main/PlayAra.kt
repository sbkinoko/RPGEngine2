package main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.domain.ScreenType
import gamescreen.battle.BattleScreen
import gamescreen.choice.ChoiceWindow
import gamescreen.map.layout.MapScreen
import gamescreen.map.viewmodel.MapViewModel
import gamescreen.mapshop.ShopMenu
import gamescreen.menu.MenuScreen
import gamescreen.text.TextWindow
import values.Colors

@Composable
fun PlayArea(
    screenType: ScreenType,
    screenSize: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (screenType) {
            ScreenType.FIELD -> {
                MapScreen(
                    modifier = Modifier.fillMaxSize(),
                    screenRatio = screenSize / MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
                )
            }

            ScreenType.BATTLE -> {
                BattleScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            ScreenType.MENU -> {
                MenuScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        ShopMenu()

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
