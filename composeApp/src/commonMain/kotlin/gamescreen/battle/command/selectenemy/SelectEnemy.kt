package gamescreen.battle.command.selectenemy

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.domain.status.StatusData
import org.koin.compose.koinInject

@Composable
fun SelectEnemy(
    playerStatus: StatusData,
    modifier: Modifier = Modifier,
    selectEnemyViewModel: SelectEnemyViewModel = koinInject(),
) {
    // fixme 画面下部のテキストエリアが他の表示領域に通知するのはおかしいので呼び出し場所を変える
    LaunchedEffect(Unit) {
        selectEnemyViewModel.updateManager()
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
