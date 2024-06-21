package layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import domain.ScreenType
import domain.common.status.MonsterStatus
import domain.common.status.param.HP
import domain.common.status.param.MP
import extension.pxToDp
import layout.battle.BattleScreen
import layout.controller.Controller
import layout.map.MapScreen
import values.Colors
import viewmodel.BattleViewModel
import viewmodel.MapViewModel
import kotlin.random.Random

@Composable
fun MainScreen() {
    val mapViewModel: MapViewModel by remember {
        mutableStateOf(
            MapViewModel()
        )
    }

    val battleViewModel: BattleViewModel by remember {
        mutableStateOf(
            BattleViewModel()
        )
    }

    var screenSize: Int by remember { mutableStateOf(0) }
    var nowScreen: ScreenType by remember {
        mutableStateOf(ScreenType.FIELD)
    }

    val bCallBack: () -> Unit = {
        // ランダムで1~5の敵を作成
        battleViewModel._monsters.value = MutableList(Random.nextInt(5) + 1) {
            MonsterStatus(
                1, "花",
                hp = HP(
                    maxValue = 10,
                ),
                mp = MP(
                    maxValue = 10,
                )
            )
        }

        nowScreen = ScreenType.BATTLE
    }
    mapViewModel.pressB = bCallBack

    battleViewModel.pressB = {
        nowScreen = ScreenType.FIELD
    }

    if (screenSize == 0) {
        Box(modifier = Modifier
            .onGloballyPositioned {
                screenSize = it.size.width
            }
            .then(
                if (screenSize != 0) {
                    Modifier.size(screenSize.pxToDp())
                } else {
                    Modifier.fillMaxSize()
                }
            )
        )
        return
    }

    MaterialTheme {
        Column {
            when (nowScreen) {
                ScreenType.FIELD -> {
                    MapScreen(
                        modifier = Modifier
                            .size(size = screenSize.pxToDp()),
                        mapViewModel = mapViewModel,
                        screenRatio = screenSize / MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
                    )
                    Controller(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = Colors.Player,
                                shape = RectangleShape,
                            )
                            .background(
                                Colors.ControllerArea,
                            ),
                        controllerCallback = mapViewModel
                    )
                }

                else -> {
                    BattleScreen(
                        modifier = Modifier.size(
                            size = screenSize.pxToDp()
                        ),
                        battleViewModel = battleViewModel,
                    )
                    Controller(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = Colors.Player,
                                shape = RectangleShape,
                            )
                            .background(
                                Colors.ControllerArea,
                            ),
                        controllerCallback = battleViewModel,
                    )
                }
            }
        }
    }
}
