package org.example.project.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import domain.map.Point
import domain.map.Square
import layout.map.Player

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
        )
    }
}
