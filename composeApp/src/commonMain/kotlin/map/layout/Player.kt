package map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.pxToDp
import common.values.Colors
import map.domain.Square

@Composable
fun Player(
    square: Square,
    screenRatio: Float,
) {
    Box(
        modifier = Modifier
            .offset(
                x = (square.x * screenRatio).pxToDp(),
                y = (square.y * screenRatio).pxToDp(),
            )
            .size((square.size * screenRatio).pxToDp())
            .background(Colors.Player),
    ) {

    }
}
