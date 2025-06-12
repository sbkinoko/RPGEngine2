package gamescreen.battle.status

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.domain.status.StatusData

@Composable
fun StatusArea(
    statusList: List<StatusData>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        statusList.forEachIndexed { index, it ->
            StatusComponent(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                status = it,
                index = index,
            )
        }
    }
}
