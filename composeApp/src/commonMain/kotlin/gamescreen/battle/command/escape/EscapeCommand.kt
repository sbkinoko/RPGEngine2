package gamescreen.battle.command.escape

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.layout.CenterText
import gamescreen.battle.command.CommandMenu
import org.koin.compose.koinInject

@Composable
fun EscapeCommand(
    modifier: Modifier,
    escapeViewModel: EscapeViewModel = koinInject(),
) {
    Column(modifier = modifier) {
        CenterText(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            text = "本当に逃げますか？"
        )

        CommandMenu(
            itemList = escapeViewModel.entries,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            menuItem = escapeViewModel,
        )
    }
}
