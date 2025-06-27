package gamescreen.battle

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.extension.pxToDp
import core.domain.status.StatusData
import values.Colors

@Composable
fun StatusDebugInfo(
    statusData: StatusData,
    size: Int,
    modifier: Modifier = Modifier,
) {
    val vScrollState = rememberScrollState()
    val hScrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(vScrollState)
                .horizontalScroll(hScrollState)
                .padding(
                    top = size.pxToDp()
                )
                .background(
                    color = Colors.BattleDebugBackGround
                )
                .padding(5.dp),
        ) {
            statusData.apply {
                Text(
                    text = name,
                )
                Text(
                    text = "HP:$hp",
                )
                Text(
                    text = "MP:$mp",
                )
                Text(
                    text = "ATK:$atk",
                )
                Text(
                    text = "DEF:$def",
                )
                Text(
                    text = "SPD:$speed",
                )
                Text(
                    text = "Condition"
                )
                conditionList.map {
                    Text(" $it")
                }
            }
        }
    }
}
