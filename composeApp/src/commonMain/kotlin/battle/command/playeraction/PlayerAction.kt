package battle.command.playeraction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import main.status.PlayerStatus
import org.koin.compose.koinInject

@Composable
fun PlayerAction(
    playerStatus: PlayerStatus,
    modifier: Modifier = Modifier,
    playerActionViewModel: PlayerActionViewModel = koinInject(),
) {
    LaunchedEffect(playerActionViewModel.playerId) {
        playerActionViewModel.init()
    }

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
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = playerActionViewModel.normalAttack,
                        battleChildViewModel = playerActionViewModel,
                    ),
                text = "攻撃"
            )

            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = playerActionViewModel.skill,
                        battleChildViewModel = playerActionViewModel,
                    ),
                text = "スキル"
            )
        }

        Row(
            modifier = equalAllocationModifier,
        ) {

        }
    }
}
