package gamescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import common.extension.pxToDp
import controller.layout.Controller
import core.repository.memory.screentype.ScreenTypeRepository
import main.MainViewModel
import org.koin.compose.koinInject
import values.Colors

@Composable
fun GameScreen(
    mainViewModel: MainViewModel = koinInject(),
) {
    val screenType = mainViewModel.nowScreenType.collectAsState(
        ScreenTypeRepository.INITIAL_SCREEN_TYPE,
    )

    val controllerCallback = mainViewModel.controllerFlow.collectAsState()

    var screenSize: Int by remember { mutableStateOf(0) }

    if (screenSize == 0) {
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    screenSize = it.size.width
                }
                .then(
                    if (screenSize != 0) {
                        Modifier.size(screenSize.pxToDp())
                    } else {
                        Modifier.fillMaxSize()
                    }
                )
        )
        return
    }

    MaterialTheme {
        Column {
            PlayArea(
                modifier = Modifier
                    .size(size = screenSize.pxToDp()),
                gameScreenType = screenType.value,
                screenSize = screenSize,
            )

            Controller(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Colors.Player,
                        shape = RectangleShape,
                    )
                    .background(
                        Colors.ControllerArea,
                    ),
                controllerCallback = controllerCallback.value,
            )
        }
    }
}
