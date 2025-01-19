package gamescreen.menushop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import gamescreen.menushop.component.MoneyComponent
import gamescreen.menushop.component.ShopComponent
import org.koin.compose.koinInject
import values.Colors

@Composable
fun ShopMenu(
    modifier: Modifier = Modifier,
    shopViewModel: ShopViewModel = koinInject(),
) {
    val isShopMenuVisible by shopViewModel
        .isShopMenuVisibleStateFlow
        .collectAsState()

    val selected by shopViewModel.selectedFlowState.collectAsState()

    val itemList by shopViewModel.shopItem

    val money by shopViewModel.moneyFlow.collectAsState()

    if (!isShopMenuVisible) {
        return
    }

    Row(
        modifier = modifier
            .clickable {
                shopViewModel.hideMenu()
            }.background(
                color = Colors.ShopBackground,
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
        ) {
            itemList.forEachIndexed { id, it ->
                ShopComponent(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                        .menuItem(
                            id = id,
                            childViewModel = shopViewModel,
                        ),
                    shopItem = it,
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
        ) {
            MoneyComponent(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        all = 5.dp
                    ),
                money = money,
            )

            Spacer(
                modifier = Modifier.weight(1f),
            )

            Box(
                modifier = Modifier.weight(1f)
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
                    text = itemList[selected].name + "の説明",
                )
            }
        }
    }
}
