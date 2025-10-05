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
    // 画面下部のテキストエリアが他の表示領域に通知するのはおかしいが、他の分岐と形を揃えるために中で処理

    //テキストの表示と矢印の表示は同じタイミングでOK
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
