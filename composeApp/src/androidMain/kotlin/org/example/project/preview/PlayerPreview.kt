package org.example.project.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import layout.map.Player

@Preview
@Composable
fun PlayerPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Player(
            x = 100f,
            y = 100f,
            size = 100f,
        )
    }
}
