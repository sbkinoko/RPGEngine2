package gamescreen.battle.command.selectally

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import common.layout.CenterText
import core.domain.status.PlayerStatus
import gamescreen.battle.command.selectenemy.SelectEnemyViewModel
import org.koin.compose.koinInject

@Composable
fun SelectAllyCommandWindow(
    playerStatus: PlayerStatus,
    modifier: Modifier = Modifier,
    selectEnemyViewModel: SelectEnemyViewModel = koinInject(),
) {
    LaunchedEffect(Unit) {
        selectEnemyViewModel.updateArrow()
    }
    CenterText(
        modifier = modifier,
        text = playerStatus.name + "の回復",
    )
}
