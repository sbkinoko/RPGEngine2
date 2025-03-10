package gamescreen.battle.command.actionphase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
fun ActionPhase(
    modifier: Modifier = Modifier,
    actionPhaseViewModel: ActionPhaseViewModel = koinInject(),
) {
    LaunchedEffect(Unit) {
        actionPhaseViewModel.init()
    }

    val playerId by actionPhaseViewModel
        .attackingStatusId
        .collectAsState()

    val actionState by actionPhaseViewModel.actionState

    Box(
        modifier = modifier
            .clickable {
                actionPhaseViewModel.pressA()
            },
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = actionPhaseViewModel.getActionText(
                playerId,
                actionState,
            ),
        )
    }
}
