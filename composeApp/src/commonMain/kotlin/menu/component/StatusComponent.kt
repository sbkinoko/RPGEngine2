package menu.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.values.Colors
import menu.status.StatusViewModel
import org.koin.compose.koinInject

@Composable
fun StatusComponent(
    modifier: Modifier = Modifier,
    statusViewModel: StatusViewModel = koinInject(),
    statusId: Int,
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Colors.StatusComponent,
                shape = RectangleShape,
            )
    ) {
        val status = statusViewModel.getStatusAt(statusId)
        Text(status.name)
        Text("HP : ${status.hp.value}/${status.hp.maxValue}")
        Text("MP : ${status.mp.value}/${status.mp.maxValue}")
    }
}
