package menu.skill.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
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
                modifier = equalAllocationModifier,
                statusId = skillTargetViewModel.user,
            )
        }
        Column(
            modifier = equalAllocationModifier
        ) {
            for (i in 0 until playerNum) {
                StatusComponent(
                    modifier = equalAllocationModifier,
                    statusId = i,
                )
            }
        }
    }
}
