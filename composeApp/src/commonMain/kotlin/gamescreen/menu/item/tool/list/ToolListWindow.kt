package gamescreen.menu.item.tool.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.itemselect.ItemListWindow
import org.koin.compose.koinInject

@Composable
fun ToolListWindow(
    modifier: Modifier = Modifier,
    toolListViewModel: ToolListViewModel = koinInject(),
) {
    ItemListWindow(
        modifier = modifier,
        itemListViewModel = toolListViewModel,
    )
}
