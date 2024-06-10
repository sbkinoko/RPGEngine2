package layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import domain.ScreenType
import domain.controller.ControllerCallback
import extension.pxToDp
import layout.controller.Controller
import layout.map.MapScreen
import values.Colors
import viewmodel.MapViewModel

@Composable
fun MainScreen() {
    val mapViewModel: MapViewModel by remember {
        mutableStateOf(
            MapViewModel()
        )
    }

    var screenSize: Int by remember { mutableStateOf(0) }
    var nowScreen: ScreenType by remember {
        mutableStateOf(ScreenType.FIELD)
    }

    val bCallBack: () -> Unit = {
        nowScreen = ScreenType.BATTLE
    }
    mapViewModel.pressB = bCallBack

    if (screenSize == 0) {
        Box(modifier = Modifier
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
            when (nowScreen) {
                ScreenType.FIELD -> {
                    MapScreen(
                        modifier = Modifier
                            .size(size = screenSize.pxToDp()),
                        mapViewModel = mapViewModel,
                        screenRatio = screenSize / MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
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
                        controllerCallback = mapViewModel
                    )
                }

                else -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(
                            size = screenSize.pxToDp(),
                        ),
                    ) {
                        Text(text = "Changed")
                    }
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
                        controllerCallback = object : ControllerCallback {
                            override fun moveStick(dx: Float, dy: Float) {

                            }

                            override var pressB: () -> Unit = {
                                nowScreen = ScreenType.FIELD
                            }

                        }
                    )
                }
            }
        }
    }
}
