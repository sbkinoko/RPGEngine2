package battle.command.skill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText

@Composable
fun SkillCommandWindow(
    modifier: Modifier = Modifier,
) {
    val viewModel = SkillCommandViewModel()
    Column(modifier = modifier) {
        Row(
            modifier = equalAllocationModifier,
        ) {
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = 0,
                        battleChildViewModel = viewModel,
                    ),
                text = "2体攻撃",
            )
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = 1,
                        battleChildViewModel = viewModel,
                    ),
                text = "2体攻撃",
            )
        }
        Row(
            modifier = equalAllocationModifier,
        ) {

        }

        Row(
            modifier = equalAllocationModifier,
        ) {

        }
    }
}
