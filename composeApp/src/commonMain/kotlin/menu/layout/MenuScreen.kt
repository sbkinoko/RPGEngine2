package menu.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import common.values.Colors
import menu.MenuViewModel
import menu.domain.MenuType
import menu.main.MainMenu
import menu.main.MainMenuItem
import menu.main.MainMenuViewModel

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel,
    modifier: Modifier = Modifier,
) {
    val menuState = menuViewModel.menuType.collectAsState(MenuType.Main)


    val mainMenuViewModel: MainMenuViewModel by remember {
        mutableStateOf(MainMenuViewModel())
    }

    mainMenuViewModel.setItems(
        List(6) {
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

    Box(modifier = modifier) {
        MainMenu(
            mainMenuViewModel = mainMenuViewModel,
            modifier = menuModifier,
        )

        when (val state = menuState.value) {
            MenuType.Main -> Unit

            MenuType.Status -> StatusMenu(
                modifier = menuModifier,
            )

            else -> Text(
                modifier = menuModifier,
                text = state.name
            )
        }
    }
}

// メニュー画面で共通でつかうmodifier
private val menuModifier = Modifier
    .fillMaxSize()
    .background(
        color = Colors.MenuBackground,
    )

fun Int.toMenuType() = when (this) {
    0 -> MenuType.Status
    1 -> MenuType.Item2
    2 -> MenuType.Item3
    3 -> MenuType.Item4
    4 -> MenuType.Item5
    5 -> MenuType.Item6
    else -> throw RuntimeException()
}
