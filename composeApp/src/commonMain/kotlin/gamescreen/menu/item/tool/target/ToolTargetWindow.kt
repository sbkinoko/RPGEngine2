package gamescreen.menu.item.tool.target

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.target.ItemTargetWindow
import org.koin.compose.koinInject

@Composable
fun ToolTargetWindow(
    modifier: Modifier = Modifier,
    toolTargetViewModel: ToolTargetViewModel = koinInject(),
) {
    ItemTargetWindow(
        modifier = modifier,
        itemTargetViewModel = toolTargetViewModel,
    )
}
