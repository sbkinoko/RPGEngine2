package gamescreen.menushop

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menushop.viewmodel.BuyViewModel
import org.koin.compose.koinInject

@Composable
fun ShopMenu(
    modifier: Modifier,
    buyViewModel: BuyViewModel = koinInject(),
) {
    ShopDetailMenu(
        shopViewModel = buyViewModel,
        modifier = modifier,
    )
}
