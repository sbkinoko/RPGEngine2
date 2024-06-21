package controller.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import controller.domain.ControllerCallback

@Composable
fun Controller(
    modifier: Modifier = Modifier,
    controllerCallback: ControllerCallback,
) {
    Column(modifier = modifier) {
        Stick(
            modifier = Modifier.weight(3f),
            controllerCallback = controllerCallback,
        )

        Buttons(
            modifier = Modifier.weight(1f)
                .fillMaxSize(),
            controllerCallback = controllerCallback,
        )
    }
}
