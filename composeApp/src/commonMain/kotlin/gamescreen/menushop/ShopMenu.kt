package gamescreen.menushop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.menushop.viewmodel.BuyViewModel
import org.koin.compose.koinInject

@Composable
fun ShopMenu(
    modifier: Modifier,
    buyViewModel: BuyViewModel = koinInject(),
    repository: ShopMenuRepository = koinInject(),
) {
    val shopType = repository
        .shopTypeStateFlow
        .collectAsState()
        .value

    when (shopType) {
        ShopType.BUY -> ShopDetailMenu(
            shopViewModel = buyViewModel,
            modifier = modifier,
        )

        ShopType.SELL -> ShopDetailMenu(
            shopViewModel = buyViewModel,
            modifier = modifier,
        )

        ShopType.CLOSE -> Unit //NOP
    }
}
