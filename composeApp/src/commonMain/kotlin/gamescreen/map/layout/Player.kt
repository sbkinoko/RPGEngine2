package gamescreen.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.extension.pxToDp
import gamescreen.map.domain.PlayerDir
import gamescreen.map.domain.collision.Square
import values.Colors

@Composable
fun Player(
    square: Square,
    screenRatio: Float,
    dir: PlayerDir,
) {
    Box(
        modifier = Modifier
            .offset(
                x = (square.x * screenRatio).pxToDp(),
                y = (square.y * screenRatio).pxToDp(),
            )
            .size((square.size * screenRatio).pxToDp())
            .background(Colors.Player),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = dir.text,
        )
    }
}
