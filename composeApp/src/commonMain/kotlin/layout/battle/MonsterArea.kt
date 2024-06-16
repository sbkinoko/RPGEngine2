package layout.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import domain.common.status.MonsterStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import viewmodel.BattleViewModel

@Composable
fun MonsterArea(
    battleViewModel: BattleViewModel,
    modifier: Modifier = Modifier,
) {
    val monsters = battleViewModel.monsters.collectAsState().value

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        monsters.forEach {
            Monster(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f),
                monsterStatus = it,
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Monster(
    monsterStatus: MonsterStatus,
    modifier: Modifier = Modifier,
) {
    if (monsterStatus.isActive) {
        Image(
            modifier = modifier,
            painter = painterResource(
                ImageBinder.bind(
                    imgId = monsterStatus.imgId,
                )
            ),
            contentScale = ContentScale.Fit,
            contentDescription = "monster",
        )
    } else {
        Spacer(
            modifier = modifier
        )
    }
}
