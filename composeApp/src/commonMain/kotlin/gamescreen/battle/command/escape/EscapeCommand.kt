package gamescreen.battle.command.escape

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.selectable
import common.layout.CenterText
import org.koin.compose.koinInject

@Composable
fun EscapeCommand(
    modifier: Modifier,
    escapeViewModel: EscapeViewModel = koinInject(),
) {
    val selected = escapeViewModel.getSelectedAsState().value

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
                    .selectable(
                        id = escapeViewModel.escapeCommand,
                        selected = selected,
                    ).clickable {
                        escapeViewModel.onClickItem(id = escapeViewModel.escapeCommand)
                    },
                text = "逃げる",
            )
            CenterText(
                modifier = equalAllocationModifier
                    .selectable(
                        id = escapeViewModel.attackCommand,
                        selected = selected,
                    ).clickable {
                        escapeViewModel.onClickItem(
                            id = escapeViewModel.attackCommand,
                        )
                    },
                text = "戦う",
            )
        }
    }
}
