package menu.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import common.layout.CenterText
import common.repository.PlayerRepository
import common.repositoryImpl.PlayerRepositoryImpl

@Composable
fun StatusMenu(
    modifier: Modifier = Modifier,
) {
    val repository: PlayerRepository = PlayerRepositoryImpl()

    var status by remember {
        mutableStateOf(repository.getPlayer(0))
    }
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            for (i in 0 until 4) {
                CenterText(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = repository.getPlayer(i).name,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            Text(status.name)
            Text("HP : ${status.hp.value}/${status.hp.maxValue}")
            Text("MP : ${status.mp.value}/${status.mp.maxValue}")
        }
    }
}
