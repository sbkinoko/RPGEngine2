package layout

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import extension.pxToDp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var x: Float by remember { mutableStateOf(0f) }
    var y: Float by remember { mutableStateOf(0f) }

    var screenSize: Int by remember { mutableStateOf(0) }
    MaterialTheme {
        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, _ ->
                            x = change.position.x
                            y = change.position.y
                        }
                    )
                }
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
                .background(
                    color = values.Colors.MapBackground
                ),
        ) {
            Column(
                modifier = Modifier.align(
                    Alignment.Center
                )
            ) {
                Text("X:$x")
                Text("Y:$y")
            }

            Player(
                x = x,
                y = y,
                size = 100f,
            )
        }
    }
}
