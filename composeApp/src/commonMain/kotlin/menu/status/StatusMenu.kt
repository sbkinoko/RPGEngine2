package menu.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.layout.CenterText
import common.values.Colors

@Composable
fun StatusMenu(
    statusViewModel: StatusViewModel,
    modifier: Modifier = Modifier,
) {
    val selectedId = statusViewModel.getSelectedAsState().value
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
            for (i in 0 until 4) {
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
                    text = statusViewModel.getStatusAt(i).name,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            val status = statusViewModel.getStatusAt(selectedId)
            Text(status.name)
            Text("HP : ${status.hp.value}/${status.hp.maxValue}")
            Text("MP : ${status.mp.value}/${status.mp.maxValue}")
        }
    }
}
