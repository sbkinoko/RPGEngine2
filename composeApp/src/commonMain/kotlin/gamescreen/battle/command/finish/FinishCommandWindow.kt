package gamescreen.battle.command.finish

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
fun FinishCommandWindow(
    modifier: Modifier = Modifier,
    battleFinishViewModel: BattleFinishViewModel = koinInject(),
) {
    Box(
        modifier = modifier
            .clickable {
                battleFinishViewModel.pressA()
            },
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "戦闘に勝利した",
        )
    }
}
