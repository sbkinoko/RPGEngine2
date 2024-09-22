package menu.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    ) {
        val status = statusViewModel.getStatusAt(statusId)
        Text(status.name)
        Text("HP : ${status.hp.value}/${status.hp.maxValue}")
        Text("MP : ${status.mp.value}/${status.mp.maxValue}")
    }
}
