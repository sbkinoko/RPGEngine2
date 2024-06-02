package layout.controller

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import extension.pxToDp
import values.Colors

@Composable
fun Stick(
    modifier: Modifier = Modifier,
) {
    var smallCircleSize: Int by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .padding(5.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    smallCircleSize = it.size.height / 3
                },
            onDraw = {
                drawCircle(
                    color = Colors.ControllerLine,
                    style = Stroke(width = 5f),
                )
            }
        )

        Canvas(
            modifier = Modifier
                .size(
                    size = smallCircleSize.pxToDp()
                ),
            onDraw = {
                drawCircle(
                    color = Colors.ControllerLine,
                    style = Stroke(width = 5f),
                )
            }
        )
    }
}
