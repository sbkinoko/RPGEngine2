package battle.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import battle.command.selectally.SelectAllyViewModel
import common.extension.menuItem
import common.status.Status
import common.values.Colors
import org.koin.compose.koinInject
import common.status.param.Point as StatusPoint

@Composable
fun StatusComponent(
    status: Status,
    index: Int,
    modifier: Modifier = Modifier,
    selectAllyViewModel: SelectAllyViewModel = koinInject(),
) {
    // 戦闘可能かどうかで色を変える
    val color = if (status.isActive) {
        Colors.IsActivePlayer
    } else {
        Colors.NotActivePlayer
    }

    val isAllySelecting = selectAllyViewModel.isAllySelecting.collectAsState().value

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize().then(
                if (isAllySelecting) {
                    Modifier.menuItem(
                        id = index,
                        battleChildViewModel = selectAllyViewModel,
                    )
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = Colors.StatusComponent,
                        shape = RectangleShape,
                    )
                }.padding(5.dp)
            ),
        ) {
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
        if (
            isAllySelecting &&
            selectAllyViewModel.targetType.canSelect(status).not()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clickable {

                    }.background(
                        color = Colors.SkillDisabled
                    )
            ) {

            }
        }
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
