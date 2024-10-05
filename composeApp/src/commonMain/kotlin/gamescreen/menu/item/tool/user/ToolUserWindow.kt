package gamescreen.menu.item.tool.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.user.UserWindow
import org.koin.compose.koinInject

@Composable
fun ToolUserWindow(
    modifier: Modifier = Modifier,
    toolUserViewModel: ToolUserViewModel = koinInject(),
) {
    val selectedId = toolUserViewModel.getSelectedAsState().value
    UserWindow(
        toolUserViewModel = toolUserViewModel,
        selectedId = selectedId,
        modifier = modifier
    )
}
