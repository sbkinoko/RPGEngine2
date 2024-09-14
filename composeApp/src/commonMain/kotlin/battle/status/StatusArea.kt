package battle.status

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import battle.command.selectally.SelectAllyViewModel
import common.extension.menuItem
import common.status.Status
import common.values.Colors
import org.koin.compose.koinInject

@Composable
fun StatusArea(
    statusList: List<Status>,
    modifier: Modifier = Modifier,
    selectAllyViewModel: SelectAllyViewModel = koinInject()
) {
    val isAllySelecting = selectAllyViewModel.isAllySelecting.collectAsState().value

    Row(
        modifier = modifier,
    ) {
        statusList.forEachIndexed { index, it ->
            StatusComponent(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .fillMaxHeight()
                    .border(
                        width = 1.dp,
                        color = Colors.StatusComponent,
                        shape = RectangleShape,
                    ).then(
                        if (isAllySelecting) {
                            Modifier.menuItem(
                                id = index,
                                battleChildViewModel = selectAllyViewModel,
                            )
                        } else {
                            Modifier
                        }
                    ),
                status = it,
            )
        }
    }
}
