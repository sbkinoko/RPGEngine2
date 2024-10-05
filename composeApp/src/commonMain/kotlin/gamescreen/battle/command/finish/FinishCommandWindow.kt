package gamescreen.battle.command.finish

import androidx.compose.runtime.Composable
import gamescreen.battle.BattleViewModel
import org.koin.compose.koinInject

@Composable
fun FinishCommandWindow(
    battleViewModel: BattleViewModel = koinInject(),
) {
    // fixme 画像がないのでチカチカする
    battleViewModel.finishBattle()
}
