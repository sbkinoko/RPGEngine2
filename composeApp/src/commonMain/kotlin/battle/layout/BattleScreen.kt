package battle.layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import battle.layout.command.CommandArea
import battle.layout.status.StatusArea
import battle.viewmodel.BattleViewModel
import common.values.Colors

@Composable
fun BattleScreen(
    battleViewModel: BattleViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        StatusArea(
            modifier = Modifier.weight(1f)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Colors.StatusArea,
                    shape = RectangleShape,
                ),
            statusList = battleViewModel.players,
        )

        MonsterArea(
            modifier = Modifier.weight(1f)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Colors.MonsterArea,
                    shape = RectangleShape,
                ),
            monsters = battleViewModel.monsters.collectAsState().value,
            selectedEnemyState = battleViewModel.selectedEnemyState.collectAsState().value,
        )

        CommandArea(
            modifier = Modifier.weight(1f)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Colors.BattleCommand,
                    shape = RectangleShape,
                ),
            battleViewModel = battleViewModel,
        )
    }
}
