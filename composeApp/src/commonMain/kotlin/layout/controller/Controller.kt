package layout.controller

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.controller.ControllerCallback

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
