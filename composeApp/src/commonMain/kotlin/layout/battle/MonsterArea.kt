package layout.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import domain.common.status.MonsterStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun MonsterArea(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Monster(
            modifier = Modifier
                .weight(1f),
            monsterStatus = MonsterStatus(
                imgId = 1,
                name = "花"
            )
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Monster(
    monsterStatus: MonsterStatus,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .padding(5.dp),
        painter = painterResource(
            ImageBinder.bind(
                imgId = monsterStatus.imgId,
            )
        ),
        contentScale = ContentScale.Fit,
        contentDescription = "monster"
    )
}
