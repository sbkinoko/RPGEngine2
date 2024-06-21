package layout.battle.status

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import domain.common.status.Status
import values.Colors

@Composable
fun StatusArea(
    statusList: List<Status>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        statusList.forEach {
            StatusComponent(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(
                        width = 1.dp,
                        color = Colors.StatusComponent,
                        shape = RectangleShape,
                    ),
                status = it,
            )
        }
    }
}
