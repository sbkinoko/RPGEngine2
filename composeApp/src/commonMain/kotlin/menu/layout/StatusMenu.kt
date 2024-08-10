package menu.layout

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.layout.CenterText
import common.repository.PlayerRepository
import common.repositoryImpl.PlayerRepositoryImpl
import common.values.Colors

@Composable
fun StatusMenu(
    modifier: Modifier = Modifier,
) {
    val repository: PlayerRepository = PlayerRepositoryImpl()

    var selectedId by remember {
        mutableStateOf(0)
    }

    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .weight(1f),
        ) {
            for (i in 0 until 4) {
                CenterText(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable {
                            selectedId = i
                        }
                        .then(
                            if (i == selectedId) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = Colors.SelectedMenu,
                                    shape = RectangleShape,
                                )
                            } else {
                                Modifier.border(
                                    width = 2.dp,
                                    color = Colors.MenuFrame,
                                    shape = RectangleShape,
                                )
                            }
                        ),
                    text = repository.getPlayer(i).name,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            val status = repository.getPlayer(selectedId)
            Text(status.name)
            Text("HP : ${status.hp.value}/${status.hp.maxValue}")
            Text("MP : ${status.mp.value}/${status.mp.maxValue}")
        }
    }
}
