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
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        StatusComponent(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = Colors.StatusComponent,
                    shape = RectangleShape,
                ),
            status = Status().apply {
                hp.maxPoint = 100
                hp.point = 50
                mp.maxPoint = 10
                mp.point = 5
            }
        )
        StatusComponent(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = Colors.StatusComponent,
                    shape = RectangleShape,
                ),
            status = Status().apply {
                hp.maxPoint = 100
                hp.point = 77
                mp.maxPoint = 111
                mp.point = 50
            }
        )
        StatusComponent(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = Colors.StatusComponent,
                    shape = RectangleShape,
                ),
            status = Status().apply {
                hp.maxPoint = 200
                hp.point = 50
                mp.maxPoint = 10
                mp.point = 50
            }
        )
        StatusComponent(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = Colors.StatusComponent,
                    shape = RectangleShape,
                ),
            status = Status().apply {
                hp.maxPoint = 10
                hp.point = 50
                mp.maxPoint = 100
                mp.point = 5
            }
        )
    }
}
