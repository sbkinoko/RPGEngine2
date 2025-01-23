package gamescreen.menushop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gamescreen.menushop.domain.ShopItem
import values.Colors

@Composable
fun ShopComponent(
    shopItem: ShopItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(
            color = Colors.MenuBackground,
        ).padding(
            all = 5.dp
        ),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            text = shopItem.name,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            text = "価格 : ${shopItem.price}",
        )
    }
}
