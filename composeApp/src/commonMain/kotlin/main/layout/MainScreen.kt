package main.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import battle.layout.BattleScreen
import battle.viewmodel.BattleViewModel
import common.extension.pxToDp
import common.status.MonsterStatus
import common.status.param.HP
import common.status.param.MP
import common.values.Colors
import controller.layout.Controller
import main.domain.ScreenType
import main.viewmodel.MainViewModel
import map.layout.MapScreen
import map.viewmodel.MapViewModel
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

    val mainViewModel: MainViewModel by remember {
        mutableStateOf(
            MainViewModel()
        )
    }
    val screenType = mainViewModel.nowScreenType.collectAsState()

    var screenSize: Int by remember { mutableStateOf(0) }

    val bCallBack: () -> Unit = {
        battleViewModel.setMonsters(
            // ランダムで1~5の敵を作成
            MutableList(Random.nextInt(5) + 1) {
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
        )

        mainViewModel.toBattle()
    }
    mapViewModel.pressB = bCallBack

    battleViewModel.pressB = {
        mainViewModel.toField()
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
            when (screenType.value) {
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