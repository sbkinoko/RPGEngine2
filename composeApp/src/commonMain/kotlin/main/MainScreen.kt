package main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import gamescreen.GameScreen
import gamescreen.start.StartScreen

@Composable
fun MainScreen() {
    var screenType by remember {
        mutableStateOf(
            ScreenType.Start
        )
    }

    when (screenType) {
        ScreenType.Start -> StartScreen {
            screenType = ScreenType.Game
        }

        ScreenType.Game -> GameScreen()
    }
}
