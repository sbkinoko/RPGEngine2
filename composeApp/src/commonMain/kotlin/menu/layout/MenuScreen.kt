package menu.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.Colors
import menu.MenuViewModel

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(
            Colors.MenuAra
        ),
    ) {

    }
}
