package battle.layout.command

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import battle.viewmodel.BattleViewModel

@Composable
fun CommandArea(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel,
) {
    MainCommand(
        modifier = modifier,
        battleViewModel = battleViewModel,
    )
}
