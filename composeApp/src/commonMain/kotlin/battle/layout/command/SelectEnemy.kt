package battle.layout.command

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.status.PlayerStatus

@Composable
fun SelectEnemy(
    playerStatus: PlayerStatus,
    modifier: Modifier = Modifier,
) {
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
