package menu.layout

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import menu.MenuViewModel
import menu.domain.MainMenuItem
import menu.domain.MenuType

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel,
    modifier: Modifier = Modifier,
) {
    val menuState = menuViewModel.menuType.collectAsState(MenuType.Main)
    when (val state = menuState.value) {
        MenuType.Main -> MainMenu(
            modifier = modifier,
            mainMenuItemList = List(6) {
                MainMenuItem(
                    text = "text${it + 1}",
                    onClick = {
                        menuViewModel.setMenuType(
                            (it + 1).toMenuType()
                        )
                    },
                )
            }
        )

        else -> Text(
            modifier = modifier,
            text = state.name
        )
    }
}

fun Int.toMenuType() = when (this) {
    1 -> MenuType.Item1
    2 -> MenuType.Item2
    3 -> MenuType.Item3
    4 -> MenuType.Item4
    5 -> MenuType.Item5
    6 -> MenuType.Item6
    else -> throw RuntimeException()
}
