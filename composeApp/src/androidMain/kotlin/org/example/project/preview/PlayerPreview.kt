package org.example.project.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gamescreen.map.domain.PlayerDir
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.Square
import gamescreen.map.layout.Player

@Preview
@Composable
fun PlayerPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Player(
            square = Square(
                displayPoint = Point(
                    x = 100f,
                    y = 100f,
                ),
                size = 100f,
            ),
            screenRatio = 1f,
            dir = PlayerDir.UP,
        )
    }
}
