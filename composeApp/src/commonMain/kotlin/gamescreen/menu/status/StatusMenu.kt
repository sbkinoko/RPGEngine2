package gamescreen.menu.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.equalAllocationModifier
import common.layout.CenterText
import gamescreen.menu.component.StatusComponent
import org.koin.compose.koinInject
import values.Colors
import values.Constants

@Composable
fun StatusMenu(
    modifier: Modifier = Modifier,
    statusViewModel: StatusViewModel = koinInject(),
) {
    val selectedId by statusViewModel.selectedFlowState.collectAsState()
    Row(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            )
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (i in 0 until Constants.playerNum) {
                CenterText(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable {
                            statusViewModel.setSelected(i)
                        }
                        .border(
                            width = 2.dp,
                            color = if (selectedId == i) Colors.SelectedMenu
                            else Colors.MenuFrame,
                            shape = RectangleShape,
                        ),
                    text = statusViewModel.getStatusAt(i).statusData.name,
                )
            }
        }

        StatusComponent(
            modifier = equalAllocationModifier
                .border(
                    width = 1.dp,
                    color = Colors.StatusComponent,
                    shape = RectangleShape,
                ),
            statusId = selectedId,
        )
    }
}
