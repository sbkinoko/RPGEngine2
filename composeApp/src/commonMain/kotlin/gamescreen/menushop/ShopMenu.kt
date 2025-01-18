package gamescreen.menushop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    if (isShopMenuVisible) {
        Box(
            modifier = modifier
                .clickable {
                    shopViewModel.hideMenu()
                }.background(
                    color = Colors.ShopBackground,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text("買い物画面")
        }
    }
}
