package gamescreen.menu.item.equipment.target

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
import gamescreen.menu.component.StatusComponent
import org.koin.compose.koinInject
import values.Colors
import values.Constants.Companion.playerNum

@Composable
fun EquipmentTargetWindow(
    modifier: Modifier = Modifier,
    targetViewModel: EquipmentTargetViewModel = koinInject(),
) {
    Row(
        modifier = modifier,
    ) {
        Column(
            modifier = equalAllocationModifier,
        ) {
            Text(
                modifier = equalAllocationModifier,
                text = targetViewModel.explain
            )
            StatusComponent(
                modifier = equalAllocationModifier
                    .border(
                        width = 1.dp,
                        color = Colors.StatusComponent,
                        shape = RectangleShape,
                    ),
                statusId = targetViewModel.user,
            )
        }
        Column(
            modifier = equalAllocationModifier
        ) {
            for (i in 0 until playerNum) {
                DisableBox(
                    modifier = equalAllocationModifier,
                    isDisable = targetViewModel.canSelect(i).not()
                ) {
                    StatusComponent(
                        modifier = Modifier
                            .fillMaxSize()
                            .menuItem(
                                id = i,
                                childViewModel = targetViewModel,
                            ),
                        statusId = i,
                    )
                }
            }
        }
    }
}
