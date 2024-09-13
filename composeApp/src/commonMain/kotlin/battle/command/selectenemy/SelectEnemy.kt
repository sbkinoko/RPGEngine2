package battle.command.selectenemy

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.status.PlayerStatus
import org.koin.compose.koinInject

@Composable
fun SelectEnemy(
    playerStatus: PlayerStatus,
    modifier: Modifier = Modifier,
    selectEnemyViewModel: SelectEnemyViewModel = koinInject(),
) {
    LaunchedEffect(Unit) {
        selectEnemyViewModel.updateArrow()
    }
    Box(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = playerStatus.name + "の攻撃",
        )
    }
}
