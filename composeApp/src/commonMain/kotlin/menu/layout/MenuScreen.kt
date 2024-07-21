package menu.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import menu.MenuViewModel
import menu.domain.MainMenuItem

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel,
    modifier: Modifier = Modifier,
) {
    MainMenu(
        modifier = modifier,
        mainMenuItemList = List(8) {
            MainMenuItem(
                text = "text${it + 1}",
                onClick = {},
            )
        }
    )
}
