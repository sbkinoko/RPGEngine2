package gamescreen.menushop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import values.Colors

@Composable
fun ExplainComponent(
    explain: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable { }
            .padding(
                all = 5.dp,
            )
            .fillMaxWidth()
            .background(
                color = Colors.MenuBackground,
            ).border(
                width = 2.dp,
                color = Colors.MenuFrame,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = explain,
        )
    }
}
