package battle.command.playeraction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.status.PlayerStatus

@Composable
fun PlayerAction(
    playerStatus: PlayerStatus,
    playerActionCallBack: PlayerActionCallBack,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = playerStatus.name,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Box(
                Modifier.weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        playerActionCallBack.attack.invoke()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "攻撃",
                    // todo 文字サイズの自動調整を実装
                    // https://zenn.dev/warahiko/articles/971f1ff0591f62
                )
            }

            Spacer(
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {

        }
    }
}
