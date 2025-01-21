package gamescreen.menushop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import gamescreen.menushop.component.AmountComponent
import gamescreen.menushop.component.ExplainComponent
import gamescreen.menushop.component.MoneyComponent
import gamescreen.menushop.component.ShopComponent
import gamescreen.menushop.domain.SubWindowType
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

    val subWindowType by shopViewModel.subWindowType

    Box(
        modifier = modifier
            .clickable {
                shopViewModel.pressB()
            }.background(
                color = Colors.ShopBackground,
            ),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
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

            Column(modifier = Modifier.weight(1f)) {
                MoneyComponent(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            all = 5.dp
                        ),
                    money = money,
                )
            }
        }

        when (subWindowType) {
            SubWindowType.EXPLAIN -> {
                SubWindow(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f),
                    )

                    ExplainComponent(
                        modifier = Modifier.weight(1f),
                        explain = itemList[selected].name + "の説明",
                    )
                }
            }

            SubWindowType.AMOUNT -> {
                SubWindow(
                    modifier = Modifier.fillMaxSize()
                        .clickable {
                            shopViewModel.pressB()
                        }.background(
                            color = Colors.OverlayMenu,
                        ),
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f),
                    )

                    AmountComponent(
                        modifier = Modifier.weight(1f)
                            .fillMaxWidth(),
                        amountData = shopViewModel.amountData,
                        onClickBuy = {
                            shopViewModel.buy(
                                selected = selected,
                            )
                        }
                    )

                    Spacer(
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
fun SubWindow(
    modifier: Modifier = Modifier,
    layout: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            layout()
        }
    }
}
