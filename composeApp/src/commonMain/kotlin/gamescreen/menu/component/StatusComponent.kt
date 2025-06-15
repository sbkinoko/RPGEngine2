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
    // fixme byにする
    val state = statusComponentViewModel
        .statusFlow
        .collectAsState()

    val statusData by statusComponentViewModel
        .statusDataFlow
        .collectAsState()

    Column(
        modifier = modifier
    ) {
        if (statusId < Constants.playerNum) {

            val parameter = statusData[statusId]
            parameter.run {
                Text(name)
                Text("HP : ${hp.point}/${hp.maxPoint}")
                Text("MP : ${mp.point}/${mp.maxPoint}")
            }

            val status = state.value[statusId]
            Text("レベル : ${status.exp.level}")
            Text("経験値 : ${status.exp.value}")
            Text("次のレベルまで : ${status.exp.needExp}")
        } else {
            Text("袋")
        }
    }
}
