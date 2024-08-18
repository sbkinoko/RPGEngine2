package battle.command.actionphase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ActionPhase(
    actionPhaseViewModel: ActionPhaseViewModel,
    modifier: Modifier = Modifier,
) {
    val playerId = actionPhaseViewModel.attackingPlayerId.collectAsState().value

    Box(
        modifier = modifier
            .clickable {
                actionPhaseViewModel.pressA()
            },
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = actionPhaseViewModel.getPlayerName(playerId) + "の" +
                    actionPhaseViewModel.targetName + "への攻撃",
        )
    }
}
