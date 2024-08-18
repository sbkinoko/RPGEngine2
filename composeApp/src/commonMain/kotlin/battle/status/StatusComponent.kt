package battle.status

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import common.status.Status
import common.values.Colors
import common.status.param.Point as StatusPoint

@Composable
fun StatusComponent(
    status: Status,
    modifier: Modifier = Modifier,
) {
    // 戦闘可能かどうかで色を変える
    val color = if (status.isActive) {
        Colors.IsActivePlayer
    } else {
        Colors.NotActivePlayer
    }

    Column(modifier = modifier) {
        Text(
            text = status.name,
            color = color,
        )
        Point(
            paramName = "HP",
            point = status.hp,
            color = color,
        )
        Point(
            paramName = "MP",
            point = status.mp,
            color = color,
        )
    }
}

@Composable
private fun Point(
    paramName: String,
    point: StatusPoint,
    color: Color
) {
    Text(
        text = "$paramName ${point.point}/${point.maxPoint}",
        color = color,
    )
}
