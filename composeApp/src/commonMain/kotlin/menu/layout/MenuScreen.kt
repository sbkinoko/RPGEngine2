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
                    text = it.toMenuType().title,
                    onClick = {
                        menuViewModel.setMenuType(
                            it.toMenuType()
                        )
                    },
                )
            }
        )

        MenuType.Status -> StatusMenu(
            modifier = modifier,
        )

        else -> Text(
            modifier = modifier,
            text = state.name
        )
    }
}

fun Int.toMenuType() = when (this) {
    0 -> MenuType.Status
    1 -> MenuType.Item2
    2 -> MenuType.Item3
    3 -> MenuType.Item4
    4 -> MenuType.Item5
    5 -> MenuType.Item6
    else -> throw RuntimeException()
}
