package menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import common.values.Colors
import gamescreen.menu.item.skill.target.SkillTargetWindow
import gamescreen.menu.item.skill.user.SkillUserWindow
import menu.domain.MenuType
import menu.main.MainMenu
import menu.skill.list.SkillListWindow
import menu.status.StatusMenu
import org.koin.compose.koinInject

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    menuViewModel: MenuViewModel = koinInject(),
) {
    val menuState = menuViewModel.menuType.collectAsState(MenuType.Main)

    Box(modifier = modifier) {
        when (val state = menuState.value) {
            MenuType.Main -> MainMenu(
                modifier = menuModifier,
            )

            MenuType.Status -> StatusMenu(
                modifier = menuModifier,
            )

            MenuType.SKILL_USER -> SkillUserWindow(
                modifier = menuModifier,
            )

            MenuType.SKILL_LST -> SkillListWindow(
                modifier = menuModifier,
            )

            MenuType.SKILL_TARGET -> SkillTargetWindow(
                modifier = menuModifier,
            )

            MenuType.Item3,
            MenuType.Item4,
            MenuType.Item5,
            MenuType.Item6 -> Text(
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
