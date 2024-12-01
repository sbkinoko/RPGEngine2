package gamescreen.battle.command.finish

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gamescreen.battle.BattleViewModel
import org.koin.compose.koinInject

@Composable
fun FinishCommandWindow(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel = koinInject(),
) {
    Box(
        modifier = modifier
            .clickable {
                battleViewModel.finishBattle()
            },
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "戦闘に勝利した",
        )
    }
}
