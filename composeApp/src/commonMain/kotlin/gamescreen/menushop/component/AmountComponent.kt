package gamescreen.menushop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import values.Colors
import values.TextData

@Composable
fun AmountComponent(
    onClickBuy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            ).border(
                width = 1.dp,
                color = Colors.MenuFrame,
            ),
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = "サンプル",
        )

        Button(
            onClick = onClickBuy,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = TextData.SHOP_BUY
            )
        }
    }
}
