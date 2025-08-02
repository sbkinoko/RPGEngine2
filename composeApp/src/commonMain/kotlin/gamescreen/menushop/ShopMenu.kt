package gamescreen.menushop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.menushop.viewmodel.BuyViewModel
import gamescreen.menushop.viewmodel.SellViewModel
import org.koin.compose.koinInject

@Composable
fun ShopMenu(
    modifier: Modifier,
    buyViewModel: BuyViewModel = koinInject(),
    sellViewModel: SellViewModel = koinInject(),
    repository: ShopMenuRepository = koinInject(),
) {
    val shopType by repository
        .shopTypeStateFlow
        .collectAsState()

    when (shopType) {
        ShopType.BUY -> ShopDetailMenu(
            shopViewModel = buyViewModel,
            modifier = modifier,
        ) {}

        ShopType.SELL -> {

            ShopDetailMenu(
                shopViewModel = sellViewModel,
                modifier = modifier,
            ) {
                sellViewModel.updateBag()
            }
        }

        ShopType.CLOSE -> Unit //NOP
    }
}
