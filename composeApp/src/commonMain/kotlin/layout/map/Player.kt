package layout.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.map.Square
import extension.pxToDp
import values.Colors

@Composable
fun Player(
    square: Square
) {
    Box(
        modifier = Modifier
            .offset(
                x = square.x.pxToDp(),
                y = square.y.pxToDp(),
            )
            .size(square.size.pxToDp())
            .background(Colors.Player),
    ) {

    }
}
