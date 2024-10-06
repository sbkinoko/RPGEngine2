package gamescreen.battle.command.item.tool

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.battle.command.item.ItemCommandWindow
import org.koin.compose.koinInject

@Composable
fun ToolCommandWindow(
    modifier: Modifier = Modifier,
    toolCommandViewModel: ToolCommandViewModel = koinInject(),
) {
    ItemCommandWindow(
        modifier = modifier,
        itemCommandViewModel = toolCommandViewModel,
    )
}
