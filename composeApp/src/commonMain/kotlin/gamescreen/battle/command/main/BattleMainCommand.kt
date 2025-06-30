package gamescreen.battle.command.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.battle.command.CommandMenu
import org.koin.compose.koinInject

@Composable
fun BattleMainCommand(
    modifier: Modifier = Modifier,
    battleMainViewModel: BattleMainViewModel = koinInject(),
) {
    Column(modifier = modifier) {
        CommandMenu(
            itemList = battleMainViewModel.entries,
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            menuItem = battleMainViewModel,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
    }
}
