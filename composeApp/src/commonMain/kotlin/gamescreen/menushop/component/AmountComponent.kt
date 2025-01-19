package gamescreen.menushop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import values.Colors

@Composable
fun AmountComponent(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            ).border(
                width = 1.dp,
                color = Colors.MenuFrame,
            ),
        text = "サンプル",
    )
}
