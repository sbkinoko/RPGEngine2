package gamescreen.menushop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import values.Colors
import values.TextData

@Composable
fun MoneyComponent(
    money: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { }
            .border(
                width = 1.dp,
                color = Colors.MenuFrame,
            ).background(
                color = Colors.MenuBackground,
            )
            .padding(5.dp),
    ) {
        Text(
            text = TextData.ShopMoney.getText()
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Text(
            text = money.toString()
        )
    }
}
