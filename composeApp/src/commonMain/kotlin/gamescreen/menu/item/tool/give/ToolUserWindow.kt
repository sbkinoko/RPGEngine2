package gamescreen.menu.item.tool.give

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.user.UserWindow
import org.koin.compose.koinInject

@Composable
fun ToolGiveUserWindow(
    modifier: Modifier = Modifier,
    toolGiveUserViewModel: ToolGiveUserViewModel = koinInject(),
) {
    UserWindow(
        modifier = modifier,
        itemUserViewModel = toolGiveUserViewModel,
    )
}
