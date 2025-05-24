package org.example.project.preview.battle

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.param.EXP
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
import data.item.skill.SkillId
import data.item.tool.ToolId
import gamescreen.battle.command.playeraction.PlayerAction
import gamescreen.battle.command.playeraction.PlayerActionViewModel

@Preview
@Composable
fun PlayerActionPreview() {
    PlayerAction(
        playerActionViewModel = PlayerActionViewModel(),
        playerStatus = PlayerStatus(
            statusData = StatusData(
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
            skillList = listOf(
                SkillId.Normal1,
            ),
            toolList = listOf(
                ToolId.HEAL1,
            ),
            exp = EXP(
                EXP.type1,
            ),
        ),
    )
}
