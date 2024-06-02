package layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import extension.pxToDp
import layout.map.MapScreen
import viewmodel.MapViewModel

@Composable
fun MainScreen() {
    val mapViewModel: MapViewModel by remember {
        mutableStateOf(
            MapViewModel(
                playerSize = 100f,
            )
        )
    }

    var screenSize: Int by remember { mutableStateOf(0) }

    if (screenSize == 0) {
        Box(modifier = Modifier
            .onGloballyPositioned {
                screenSize = it.size.width
                mapViewModel.initBackgroundCellManager(screenSize)
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

    MapScreen(
        mapViewModel = mapViewModel,
        screenSize = screenSize,
    )
}
