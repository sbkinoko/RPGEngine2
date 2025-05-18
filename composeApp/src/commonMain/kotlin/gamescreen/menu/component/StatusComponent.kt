package gamescreen.menu.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import values.Constants

@Composable
fun StatusComponent(
    statusId: Int,
    modifier: Modifier = Modifier,
    statusComponentViewModel: StatusComponentViewModel = koinInject(),
) {
    val state = statusComponentViewModel
        .statusFlow
        .collectAsState()

    Column(
        modifier = modifier
    ) {
        if (statusId < Constants.playerNum) {
            val status = state.value[statusId]
            status.statusData.run {
                Text(name)
                Text("HP : ${hp.value}/${hp.max}")
                Text("MP : ${mp.value}/${mp.max}")
            }
            Text("レベル : ${status.exp.level}")
            Text("経験値 : ${status.exp.value}")
            Text("次のレベルまで : ${status.exp.needExp}")
        } else {
            Text("袋")
        }
    }
}
