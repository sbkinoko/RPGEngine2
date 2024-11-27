package gamescreen.battle

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import gamescreen.battle.command.CommandArea
import gamescreen.battle.monster.MonsterArea
import gamescreen.battle.status.StatusArea
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import values.Colors

@Composable
fun BattleScreen(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel = koinInject(),
) {
    val monsters by battleViewModel.monsters.collectAsState()
    val playerStatusList = battleViewModel
        .playerStatusFlow
        .collectAsState()

    if (monsters.isEmpty()) {
        LaunchedEffect(Unit) {
            delay(10)
            battleViewModel.reloadMonster()
        }
    }

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
            statusList = playerStatusList.value,
        )

        battleViewModel.apply {
            MonsterArea(
                modifier = Modifier.weight(1f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Colors.MonsterArea,
                        shape = RectangleShape,
                    ),
                monsters = monsters,
            )
        }

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
