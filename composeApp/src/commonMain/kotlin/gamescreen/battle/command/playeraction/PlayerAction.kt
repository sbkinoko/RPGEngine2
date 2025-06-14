package gamescreen.battle.command.playeraction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import core.domain.status.StatusData
import core.domain.status.StatusType
import org.koin.compose.koinInject

@Composable
fun PlayerAction(
    playerStatus: StatusData<StatusType.Player>,
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
                        childViewModel = playerActionViewModel,
                    ),
                text = "攻撃"
            )

            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = playerActionViewModel.skill,
                        childViewModel = playerActionViewModel,
                    ),
                text = "スキル"
            )
        }

        Row(
            modifier = equalAllocationModifier,
        ) {
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = playerActionViewModel.tool,
                        childViewModel = playerActionViewModel,
                    ),
                text = "道具"
            )
            Spacer(
                modifier = equalAllocationModifier,
            )
        }
    }
}
