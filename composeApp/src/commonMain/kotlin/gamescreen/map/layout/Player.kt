package gamescreen.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.extension.pxToDp
import gamescreen.map.viewmodel.MapViewModel
import values.Colors

@Composable
fun Player(
    mapViewModel: MapViewModel,
    screenRatio: Float,
    modifier: Modifier = Modifier,
) {
    val player by mapViewModel
        .playerSquare
        .collectAsState()

    Box(
        modifier = modifier
            .offset(
                x = (player.square.x * screenRatio).pxToDp(),
                y = (player.square.y * screenRatio).pxToDp(),
            )
            .size((player.square.size * screenRatio).pxToDp())
            .background(Colors.Player)
            .clickable {
                mapViewModel.touchCharacter()
            },
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
            .size((player.eventSquare.size * screenRatio).pxToDp())
            .background(
                if (player.eventType.canEvent) {
                    Colors.CanEventCollision
                } else {
                    Colors.NotEventCollision
                }
            )
            .clickable {
                mapViewModel.touchEventSquare()
            },
    )
}
