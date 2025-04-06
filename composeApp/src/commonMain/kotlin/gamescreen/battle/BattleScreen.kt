package gamescreen.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import gamescreen.battle.command.CommandArea
import gamescreen.battle.domain.toResource
import gamescreen.battle.monster.MonsterArea
import gamescreen.battle.status.StatusArea
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import values.Colors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BattleScreen(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel = koinInject(),
) {
    val monsters by battleViewModel
        .monsterStatusFlow
        .collectAsState()

    val flashState by battleViewModel.flashStateFlow.collectAsState()

    val playerStatusList = battleViewModel
        .playerStatusFlow
        .collectAsState()

    val backgroundType by battleViewModel
        .battleBackgroundTypeFlow
        .collectAsState()

    if (monsters.isEmpty()) {
        LaunchedEffect(Unit) {
            delay(10)
            battleViewModel.reloadMonster()
        }
    }

    Box(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(
                backgroundType.toResource()
            ),
            contentDescription = "戦闘背景"
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StatusArea(
                modifier = Modifier.weight(1f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Colors.StatusArea,
                        shape = RectangleShape,
                    ),
                statusList = playerStatusList.value,
            )


            MonsterArea(
                modifier = Modifier.weight(1f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Colors.MonsterArea,
                        shape = RectangleShape,
                    ),
                monsters = monsters,
                flashState = flashState,
            )


            CommandArea(
                modifier = Modifier.weight(1f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Colors.BattleCommand,
                        shape = RectangleShape,
                    ),
                battleViewModel = battleViewModel,
            )
        }
    }
}
