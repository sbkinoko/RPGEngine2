package layout.battle.status

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.common.status.Status
import domain.common.status.Point as StatusPoint

@Composable
fun StatusComponent(
    status: Status,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Text(
            text = "player"
        )
        Point(
            "HP",
            status.hp,
        )
        Point(
            "MP",
            status.mp,
        )
    }
}

@Composable
private fun Point(
    paramName: String,
    point: StatusPoint,
) {
    Text(
        text = "$paramName ${point.point}/${point.maxPoint}"
    )
}
