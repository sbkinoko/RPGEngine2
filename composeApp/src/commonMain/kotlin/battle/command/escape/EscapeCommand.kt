package battle.command.escape

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.selectable
import common.layout.CenterText

@Composable
fun EscapeCommand(
    modifier: Modifier,
) {
    val selected = 0
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
                        id = 0,
                        selected = selected,
                    ).clickable {

                    },
                text = "逃げる",
            )
            CenterText(
                modifier = equalAllocationModifier
                    .selectable(
                        id = 1,
                        selected = selected,
                    ).clickable {

                    },
                text = "戦う",
            )
        }
    }
}
