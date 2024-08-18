package battle.command.playeraction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.extension.equalAllocationModifier
import common.extension.selectable
import common.status.PlayerStatus

@Composable
fun PlayerAction(
    playerStatus: PlayerStatus,
    playerActionViewModel: PlayerActionViewModel,
    modifier: Modifier = Modifier,
) {
    val selected = playerActionViewModel.getSelectedAsState().value
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = playerStatus.name,
        )
        Row(
            modifier = equalAllocationModifier,
        ) {
            Box(
                modifier = equalAllocationModifier
                    .selectable(
                        id = 0,
                        selected = selected,
                    )
                    .clickable {
                        playerActionViewModel.pressA()
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
                modifier = equalAllocationModifier
                    .selectable(
                        id = 1,
                        selected = selected,
                    ),
            )
        }
        Row(
            modifier = equalAllocationModifier,
        ) {

        }
    }
}
