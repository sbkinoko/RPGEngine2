package gamescreen.menu.item.tool.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.user.UserWindow
import org.koin.compose.koinInject

@Composable
fun ToolUserWindow(
    modifier: Modifier = Modifier,
    toolUserViewModel: ToolUserViewModel = koinInject(),
) {
    UserWindow(
        modifier = modifier,
        itemUserViewModel = toolUserViewModel,
    )
}
