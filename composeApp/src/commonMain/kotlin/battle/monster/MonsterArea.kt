package battle.monster

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import battle.command.selectenemy.SelectEnemyViewModel
import common.status.MonsterStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun MonsterArea(
    monsters: List<MonsterStatus>,
    selectEnemyViewModel: SelectEnemyViewModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        monsters.mapIndexed { index, monsterStatus ->
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Arrow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    index = index,
                    selectedEnemyState = selectEnemyViewModel.selectedEnemyState.collectAsState().value,
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            if (monsterStatus.isActive) {
                                selectEnemyViewModel.selectAttackMonster(
                                    monsterId = index,
                                )
                            }
                        },
                ) {
                    Monster(
                        modifier = Modifier.fillMaxWidth(),
                        monsterStatus = monsterStatus,
                    )
                }
            }
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
