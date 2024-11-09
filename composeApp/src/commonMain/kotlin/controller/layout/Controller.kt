package controller.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import controller.domain.ControllerCallback
import values.LayoutConst

@Composable
fun Controller(
    modifier: Modifier = Modifier,
    controllerCallback: ControllerCallback,
) {
    Column(modifier = modifier) {
        Stick(
            modifier = Modifier
                .weight(LayoutConst.STICK_WIGHT)
                .fillMaxSize(),
            controllerCallback = controllerCallback,
        )

        Buttons(
            modifier = Modifier
                .weight(LayoutConst.BUTTON_WIGHT)
                .fillMaxSize(),
            controllerCallback = controllerCallback,
        )
    }
}
