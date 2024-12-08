package org.example.project.preview.battle

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.domain.status.PlayerStatus
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import gamescreen.battle.command.playeraction.PlayerAction
import gamescreen.battle.command.playeraction.PlayerActionViewModel

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
            ),
            toolList = listOf(
                0,
            ),
            exp = EXP(
                EXP.type1,
            ),
        ),
    )
}
