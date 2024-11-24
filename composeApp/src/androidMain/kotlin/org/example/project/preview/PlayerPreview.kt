package org.example.project.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gamescreen.map.domain.PlayerDir
import gamescreen.map.layout.Player

@Preview
@Composable
fun PlayerPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Player(
            dir = PlayerDir.UP,
        )
    }
}
