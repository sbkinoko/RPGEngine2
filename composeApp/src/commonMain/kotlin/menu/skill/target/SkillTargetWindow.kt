package menu.skill.target

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.values.Colors
import common.values.playerNum
import menu.component.StatusComponent
import org.koin.compose.koinInject

@Composable
fun SkillTargetWindow(
    modifier: Modifier = Modifier,
    skillTargetViewModel: SkillTargetViewModel = koinInject(),
) {
    Row(
        modifier = modifier,
    ) {
        Column(
            modifier = equalAllocationModifier,
        ) {
            Text(
                modifier = equalAllocationModifier,
                text = skillTargetViewModel.explain
            )
            StatusComponent(
                modifier = equalAllocationModifier
                    .border(
                        width = 1.dp,
                        color = Colors.StatusComponent,
                        shape = RectangleShape,
                    ),
                statusId = skillTargetViewModel.user,
            )
        }
        Column(
            modifier = equalAllocationModifier
        ) {
            for (i in 0 until playerNum) {
                StatusComponent(
                    modifier = equalAllocationModifier
                        .menuItem(
                            id = i,
                            childViewModel = skillTargetViewModel,
                        ),
                    statusId = i,
                )
            }
        }
    }
}
