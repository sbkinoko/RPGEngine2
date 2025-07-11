package gamescreen.battle.monster

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import core.domain.status.StatusData
import core.domain.status.monster.MonsterStatus
import gamescreen.battle.StatusDebugInfo
import gamescreen.battle.command.selectenemy.SelectEnemyViewModel
import gamescreen.battle.effect.AttackEffect
import gamescreen.battle.repository.attackeffect.AttackEffectInfo
import gamescreen.battle.repository.flash.FlashInfo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun MonsterArea(
    // fixme imgIDだけ渡す
    monsters: List<MonsterStatus>,
    monsterStatusList: List<StatusData>,
    flashState: List<FlashInfo>,
    attackEffectInfo: List<AttackEffectInfo>,
    modifier: Modifier = Modifier,
    selectEnemyViewModel: SelectEnemyViewModel = koinInject(),
) {
    val selectEnemyState by selectEnemyViewModel
        .selectedEnemyState
        .collectAsState()

    var boxSize by remember {
        mutableIntStateOf(Int.MAX_VALUE)
    }

    Box(
        modifier = modifier,
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (index: Int in monsters.indices) {
                val statusData = monsterStatusList[index]

                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
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
                                    if (statusData.isActive) {
                                        selectEnemyViewModel.selectAttackMonster(
                                            monsterId = index,
                                        )
                                    }
                                }.onGloballyPositioned {
                                    boxSize = it.size.height
                                },
                        ) {
                            flashState[index].apply {
                                if (
                                //攻撃エフェクト中
                                    attackEffectInfo[index].isVisible ||
                                    //点滅中かつ表示中
                                    (isFlashing && isVisible) ||
                                    //　生存状態かつ非点滅
                                    (!isFlashing && statusData.isActive)
                                ) {
                                    Monster(
                                        modifier = Modifier.fillMaxWidth(),
                                        imgId = monsters[index].imgId,
                                    )
                                }
                            }

                            StatusDebugInfo(
                                statusData = statusData,
                                size = boxSize,
                            )
                        }
                    }

                    AttackEffect(
                        attackEffectInfo[index],
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Monster(
    imgId: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier,
        painter = painterResource(
            ImageBinder.bind(
                imgId = imgId,
            )
        ),
        contentScale = ContentScale.Fit,
        contentDescription = "monster",
    )
}
