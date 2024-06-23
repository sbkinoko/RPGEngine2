package battle.layout

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Arrow(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "↓",
        textAlign = TextAlign.Center,
    )
}
