package gamescreen.battle.command.escape

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import org.koin.compose.koinInject

@Composable
fun EscapeCommand(
    modifier: Modifier,
    escapeViewModel: EscapeViewModel = koinInject(),
) {
    Column(modifier = modifier) {
        CenterText(
            modifier = equalAllocationModifier,
            text = "本当に逃げますか？"
        )

        Row(
            modifier = equalAllocationModifier,
        ) {
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = escapeViewModel.escapeCommand,
                        childViewModel = escapeViewModel,
                    ),
                text = "逃げる",
            )
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = escapeViewModel.attackCommand,
                        childViewModel = escapeViewModel,
                    ),
                text = "戦う",
            )
        }
    }
}
