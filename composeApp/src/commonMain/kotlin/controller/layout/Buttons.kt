package controller.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.LayoutConst.Companion.BUTTON_PADDING
import controller.domain.ControllerCallback

@Composable
fun Buttons(
    controllerCallback: ControllerCallback,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(BUTTON_PADDING),
        horizontalArrangement = Arrangement.spacedBy(BUTTON_PADDING)
    ) {
        val buttonModifier = Modifier
            .weight(1f)
            .fillMaxHeight()

        Button(
            modifier = buttonModifier,
            onClick = {
                controllerCallback.pressA()
            },
        ) {
            Text(text = "A")
        }

        Button(
            modifier = buttonModifier,
            onClick = {
                controllerCallback.pressB()
            },
        ) {
            Text(text = "B")
        }

        Button(
            modifier = buttonModifier,
            onClick = {
                controllerCallback.pressM()
            },
        ) {
            Text(text = "M")
        }
    }
}
