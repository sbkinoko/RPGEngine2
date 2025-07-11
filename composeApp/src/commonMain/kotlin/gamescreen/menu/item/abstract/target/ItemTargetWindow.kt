package gamescreen.menu.item.abstract.target

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
import values.Colors
import values.Constants.Companion.playerNum

@Composable
fun ItemTargetWindow(
    modifier: Modifier = Modifier,
    itemTargetViewModel: TargetViewModel<*, *>,
) {

    Row(
        modifier = modifier,
    ) {
        Column(
            modifier = equalAllocationModifier,
        ) {
            Text(
                modifier = equalAllocationModifier,
                text = itemTargetViewModel.explain
            )
            StatusComponent(
                modifier = equalAllocationModifier
                    .border(
                        width = 1.dp,
                        color = Colors.StatusComponent,
                        shape = RectangleShape,
                    ),
                statusId = itemTargetViewModel.user,
            )
        }
        Column(
            modifier = equalAllocationModifier
        ) {
            for (i in 0 until playerNum) {
                DisableBox(
                    modifier = equalAllocationModifier,
                    isDisable = itemTargetViewModel.canSelect(i).not()
                ) {
                    StatusComponent(
                        modifier = Modifier
                            .fillMaxSize()
                            .menuItem(
                                id = i,
                                menuItem = itemTargetViewModel,
                            ),
                        statusId = i,
                    )
                }
            }
        }
    }
}
