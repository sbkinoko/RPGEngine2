package gamescreen.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.extension.pxToDp
import gamescreen.map.domain.Player
import values.Colors

// fixme タップ用のエリアを分ける
@Composable
fun Player(
    player: Player,
    screenRatio: Float,
    clickPlayer: () -> Unit,
    clickEventSquare: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .offset(
                x = (player.square.x * screenRatio).pxToDp(),
                y = (player.square.y * screenRatio).pxToDp(),
            )
            .size(
                width = (player.square.width * screenRatio).pxToDp(),
                height = (player.square.height * screenRatio).pxToDp(),
            )
            .background(Colors.Player)
            .clickable(onClick = clickPlayer),
//
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = player.dir.text,
        )
    }

    Spacer(
        modifier = Modifier
            .offset(
                x = (player.eventSquare.x * screenRatio).pxToDp(),
                y = (player.eventSquare.y * screenRatio).pxToDp(),
            )
            .size(
                width = (player.square.width * screenRatio).pxToDp(),
                height = (player.square.height * screenRatio).pxToDp(),
            )
            .background(
                if (player.eventType.canEvent) {
                    Colors.CanEventCollision
                } else {
                    Colors.NotEventCollision
                }
            )
            .clickable(
                onClick = clickEventSquare
            ),
//                mapViewModel.touchEventSquare()
    )
}
