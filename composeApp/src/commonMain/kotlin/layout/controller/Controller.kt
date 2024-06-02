package layout.controller

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Controller(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Stick(
            modifier = Modifier.weight(3f),
        )

        Buttons(
            modifier = Modifier.weight(1f)
                .fillMaxSize(),
        )
    }
}
