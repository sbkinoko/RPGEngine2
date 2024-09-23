package org.example.project.preview.battle

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import battle.command.playeraction.PlayerAction
import battle.command.playeraction.PlayerActionViewModel
import core.domain.status.PlayerStatus
import core.domain.status.param.HP
import core.domain.status.param.MP

@Preview
@Composable
fun PlayerActionPreview() {
    PlayerAction(
        playerActionViewModel = PlayerActionViewModel(),
        playerStatus = PlayerStatus(
            "test",
            hp = HP(
                maxValue = 100,
                value = 50,
            ),
            mp = MP(
                maxValue = 10,
                value = 5,
            ),
            skillList = listOf(
                0,
            )
        ),
    )
}
