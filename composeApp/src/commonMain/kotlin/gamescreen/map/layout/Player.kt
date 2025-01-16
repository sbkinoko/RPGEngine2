package gamescreen.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    val dir = mapViewModel.dirFlow.collectAsState()
    val square = mapViewModel
        .playerSquare
        .collectAsState()

    Box(
        modifier = modifier
            .offset(
                x = (square.value.x * screenRatio).pxToDp(),
                y = (square.value.y * screenRatio).pxToDp(),
            )
            .size((square.value.size * screenRatio).pxToDp())
            .background(Colors.Player)
            .clickable {
                mapViewModel.touchCharacter()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = dir.value.text,
        )
    }
}
