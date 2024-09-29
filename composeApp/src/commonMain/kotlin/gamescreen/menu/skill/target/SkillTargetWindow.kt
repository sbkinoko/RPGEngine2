package menu.skill.target

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.DisableBox
import common.values.Colors
import common.values.playerNum
import core.WithConfirmAndTextWindow
import menu.component.StatusComponent
import org.koin.compose.koinInject

@Composable
fun SkillTargetWindow(
    modifier: Modifier = Modifier,
    skillTargetViewModel: SkillTargetViewModel = koinInject(),
) {
    WithConfirmAndTextWindow(
        modifier = modifier,
        confirmCallBack = {
            skillTargetViewModel.selectYes()
        },
        textCallBack = {
            skillTargetViewModel.backWindow()
        },
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
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
                    DisableBox(
                        modifier = equalAllocationModifier,
                        isDisable = skillTargetViewModel.canSelect(i).not()
                    ) {
                        StatusComponent(
                            modifier = Modifier
                                .fillMaxSize()
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
    }
}
