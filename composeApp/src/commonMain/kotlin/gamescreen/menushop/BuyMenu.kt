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
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.domain.SubWindowType
import gamescreen.menushop.viewmodel.AbstractShopViewModel
import values.Colors

@Composable
fun BuyMenu(
    shopViewModel: AbstractShopViewModel,
    modifier: Modifier = Modifier,
) {
    val shopType by shopViewModel
        .isShopMenuVisibleStateFlow
        .collectAsState()

    val selected by shopViewModel.selectedFlowState.collectAsState()

    val itemList by shopViewModel.shopItemStateFlow.collectAsState()

    val money by shopViewModel.moneyFlow.collectAsState()

    if (shopType == ShopType.CLOSE) {
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
                                menuItem = shopViewModel,
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
                    columnModifier = Modifier.padding(5.dp)
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f),
                    )

                    ExplainComponent(
                        modifier = Modifier.weight(1f),
                        explain = shopViewModel.getExplainAt(
                            selected
                        ),
                    )
                }
            }

            SubWindowType.AMOUNT -> {
                shopViewModel.reset()

                SubWindow(
                    modifier = Modifier.fillMaxSize()
                        .clickable {
                            shopViewModel.pressB()
                        }.background(
                            color = Colors.OverlayMenu,
                        ),
                    columnModifier = Modifier.padding(5.dp)
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f),
                    )

                    AmountComponent(
                        modifier = Modifier.weight(1f)
                            .fillMaxWidth(),
                        amountData = shopViewModel.amountData,
                        onClickBuy = {
                            shopViewModel.decideNum(
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
    columnModifier: Modifier = Modifier,
    layout: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.weight(1f)
                .then(
                    columnModifier
                ),
        ) {
            layout()
        }
    }
}
