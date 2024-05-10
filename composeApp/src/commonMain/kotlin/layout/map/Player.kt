package layout.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import extension.pxToDp
import values.Colors

@Composable
fun Player(
    x: Float,
    y: Float,
    size: Float,
) {
    Box(
        modifier = Modifier
            .offset(
                x = x.pxToDp(),
                y = y.pxToDp(),
            )
            .size(size.pxToDp())
            .background(Colors.Player),
    ) {

    }
}
