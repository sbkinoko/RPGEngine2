package gamescreen.battle.status

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.layout.DisableBox
import core.domain.status.StatusData
import core.domain.status.param.StatusParameterWithMax
import gamescreen.battle.StatusDebugInfo
import gamescreen.battle.command.selectally.SelectAllyViewModel
import org.koin.compose.koinInject
import values.Colors

@Composable
fun StatusComponent(
    status: StatusData,
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

    var boxSize by remember {
        mutableIntStateOf(0)
    }

    DisableBox(
        modifier = modifier
            .onGloballyPositioned {
                boxSize = it.size.height
            }.then(
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
                }
            ),
        isDisable = (isAllySelecting &&
                selectAllyViewModel.targetStatusType.canSelect(status).not()),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(5.dp),
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

        StatusDebugInfo(
            statusData = status,
            size = boxSize,
        )
    }
}

@Composable
private fun Point(
    paramName: String,
    point: StatusParameterWithMax<*>,
    color: Color,
) {
    Text(
        text = "$paramName ${point.point}/${point.maxPoint}",
        color = color,
    )
}
