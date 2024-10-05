package gamescreen.menu.item.tool.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.itemselect.ItemListWindow
import org.koin.compose.koinInject

@Composable
fun ToolListWindow(
    modifier: Modifier = Modifier,
    toolUserViewModel: ToolListViewModel = koinInject(),
) {
    ItemListWindow(
        modifier = modifier,
        itemListViewModel = toolUserViewModel,
    )
}
