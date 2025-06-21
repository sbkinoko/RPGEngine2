package gamescreen.menu.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import values.Constants

@Composable
fun StatusComponent(
    statusId: Int,
    modifier: Modifier = Modifier,
    statusComponentViewModel: StatusComponentViewModel = koinInject(),
) {
    val state by statusComponentViewModel
        .statusFlow
        .collectAsState()

    val statusData by statusComponentViewModel
        .statusDataFlow
        .collectAsState()

    Column(
        modifier = modifier
    ) {
        if (statusId < Constants.playerNum) {
            statusData[statusId].apply {
                Text(name)
                Text("HP : ${hp.point}/${hp.maxPoint}")
                Text("MP : ${mp.point}/${mp.maxPoint}")
                Text("ATK:${atk.value}")
                Text("DEF:${def.value}")
                Text("SPD:${speed.value}")
            }

            state[statusId].apply {
                Text("レベル : ${exp.level}")
                Text("経験値 : ${exp.value}")
                Text("次のレベルまで : ${exp.needExp}")
            }
        } else {
            Text("袋")
        }
    }
}
