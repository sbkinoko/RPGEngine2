package gamescreen.menu.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.repository.player.PlayerRepository
import org.koin.compose.koinInject
import values.Constants

@Composable
fun StatusComponent(
    modifier: Modifier = Modifier,
    playerRepository: PlayerRepository = koinInject(),
    statusId: Int,
) {
    val state = playerRepository.getFlowAsState()

    Column(
        modifier = modifier
    ) {
        if (statusId < Constants.playerNum) {
            val status = state.value[statusId]
            Text(status.name)
            Text("HP : ${status.hp.value}/${status.hp.maxValue}")
            Text("MP : ${status.mp.value}/${status.mp.maxValue}")
        } else {
            Text("袋")
        }
    }
}
