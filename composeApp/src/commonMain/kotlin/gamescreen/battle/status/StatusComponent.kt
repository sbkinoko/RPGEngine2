package gamescreen.battle.status

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.layout.DisableBox
import core.domain.status.Status
import gamescreen.battle.command.selectally.SelectAllyViewModel
import org.koin.compose.koinInject
import values.Colors
import core.domain.status.param.Point as StatusPoint

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

    val isAllySelecting by selectAllyViewModel
        .isAllySelecting
        .collectAsState()


    DisableBox(
        modifier = modifier,
        isDisable = (isAllySelecting &&
                selectAllyViewModel.targetType.canSelect(status).not()),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().then(
                if (isAllySelecting) {
                    // 味方選択中は選択可能にする
                    Modifier.menuItem(
                        id = index,
                        childViewModel = selectAllyViewModel,
                    )
                } else {
                    // ただの表示欄
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
    }
}

@Composable
private fun Point(
    paramName: String,
    point: StatusPoint,
    color: Color,
) {
    Text(
        text = "$paramName ${point.point}/${point.maxPoint}",
        color = color,
    )
}
