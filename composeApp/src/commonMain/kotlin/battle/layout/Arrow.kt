package battle.layout

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Arrow(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "↓",
    )
}
