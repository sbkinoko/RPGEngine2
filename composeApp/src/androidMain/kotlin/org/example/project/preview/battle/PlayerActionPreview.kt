package org.example.project.preview.battle

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.domain.status.StatusData
import core.domain.status.StatusType
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
import gamescreen.battle.command.playeraction.PlayerAction
import gamescreen.battle.command.playeraction.PlayerActionViewModel
import org.koin.compose.koinInject

@Preview
@Composable
fun PlayerActionPreview(
    playerActionViewModel: PlayerActionViewModel = koinInject(),
) {
    PlayerAction(
        playerActionViewModel = playerActionViewModel,
        playerStatus = StatusData<StatusType.Player>(
            "test",
            hp = StatusParameterWithMax(
                maxPoint = 100,
                point = 50,
            ),
            mp = StatusParameterWithMax(
                maxPoint = 10,
                point = 5,
            ),
            speed = StatusParameter(10),
            conditionList = emptyList(),
        ),
    )
}
