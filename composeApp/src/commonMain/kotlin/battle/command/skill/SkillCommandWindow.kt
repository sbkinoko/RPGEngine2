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
    skillCommandViewModel: SkillCommandViewModel,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = equalAllocationModifier,
        ) {
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = 0,
                        battleChildViewModel = skillCommandViewModel,
                    ),
                text = "2体攻撃",
            )
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = 1,
                        battleChildViewModel = skillCommandViewModel,
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
