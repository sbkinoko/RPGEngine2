package gamescreen.battle.command.finish

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.domain.BattleResult
import org.koin.compose.koinInject

@Composable
fun FinishCommandWindow(
    battleResult: BattleResult,
    modifier: Modifier = Modifier,
    battleFinishViewModel: BattleFinishViewModel = koinInject(),
) {
    val textFlow by battleFinishViewModel.textFlow.collectAsState()

    LaunchedEffect(Unit) {
        battleFinishViewModel.init(
            battleResult = battleResult
        )
    }

    Box(
        modifier = modifier
            .clickable {
                battleFinishViewModel.pressA()
            },
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = textFlow,
        )
    }
}
