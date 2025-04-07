package gamescreen.battle.monster

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import core.domain.status.monster.MonsterStatus
import gamescreen.battle.command.selectenemy.SelectEnemyViewModel
import gamescreen.battle.repository.flash.FlashInfo
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import values.Colors

@Composable
fun MonsterArea(
    monsters: List<MonsterStatus>,
    flashState: List<FlashInfo>,
    modifier: Modifier = Modifier,
    selectEnemyViewModel: SelectEnemyViewModel = koinInject(),
) {
    val selectEnemyState by selectEnemyViewModel
        .selectedEnemyState
        .collectAsState()

    Box(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
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
                        selectedEnemyState = selectEnemyState,
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
                        flashState[index].apply {
                            if ((isFlashing && isVisible) ||
                                (isFlashing.not() && monsterStatus.isActive)
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
        }

        AttackEffect()
    }
}

private val rateList = listOf(
    0f,
    0.5f,
    0.6f,
    0.7f,
    0.8f,
    0.9f,
    1f,
    1f,
)

@Composable
private fun AttackEffect() {
    var center by remember {
        mutableStateOf(0f)
    }

    var maxHeight by remember {
        mutableStateOf(0f)
    }

    var index by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(50)
            index++

            if (rateList.size <= index) {
                index = 0
            }
        }
    }

    val path = Path().apply {
        moveTo(center + 40f, 0f)
        lineTo(center - 40 + 80 * (1 - rateList[index]), maxHeight * rateList[index])
        lineTo(center + 80f, 40f)
        close()
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
            .onGloballyPositioned {
                center = it.size.width / 2f
                maxHeight = it.size.height.toFloat()
            }
    ) {
        drawPath(
            path = path,
            color = Colors.AttackFill,
            style = Fill,
        )

        drawPath(
            path = path,
            color = Colors.AttackBorder,
            style = Stroke(
                width = 2f,
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
        modifier = modifier,
        painter = painterResource(
            ImageBinder.bind(
                imgId = monsterStatus.imgId,
            )
        ),
        contentScale = ContentScale.Fit,
        contentDescription = "monster",
    )
}
