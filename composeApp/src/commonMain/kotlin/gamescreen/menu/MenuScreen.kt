package gamescreen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.equipment.list.EquipmentListWindow
import gamescreen.menu.item.equipment.target.EquipmentTargetWindow
import gamescreen.menu.item.equipment.user.EquipmentUserWindow
import gamescreen.menu.item.skill.list.SkillListWindow
import gamescreen.menu.item.skill.target.SkillTargetWindow
import gamescreen.menu.item.skill.user.SkillUserWindow
import gamescreen.menu.item.tool.give.ToolGiveUserWindow
import gamescreen.menu.item.tool.list.ToolListWindow
import gamescreen.menu.item.tool.target.ToolTargetWindow
import gamescreen.menu.item.tool.user.ToolUserWindow
import gamescreen.menu.main.MainMenu
import gamescreen.menu.status.StatusMenu
import org.koin.compose.koinInject
import values.Colors

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

            MenuType.TOOL_USER -> ToolUserWindow(
                modifier = menuModifier,
            )

            MenuType.TOOL_LIST -> ToolListWindow(
                modifier = menuModifier,
            )

            MenuType.TOOL_TARGET -> ToolTargetWindow(
                modifier = menuModifier,
            )

            MenuType.TOOL_GIVE -> ToolGiveUserWindow(
                modifier = menuModifier,
            )

            MenuType.EQUIPMENT_USER -> EquipmentUserWindow(
                modifier = menuModifier,
            )

            MenuType.EQUIPMENT_LIST -> EquipmentListWindow(
                modifier = menuModifier,
            )

            MenuType.EQUIPMENT_TARGET -> EquipmentTargetWindow(
                modifier = menuModifier,
            )

            MenuType.Item3,
            MenuType.Collision,
            MenuType.Item5,
            MenuType.Item6,
                -> Text(
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
