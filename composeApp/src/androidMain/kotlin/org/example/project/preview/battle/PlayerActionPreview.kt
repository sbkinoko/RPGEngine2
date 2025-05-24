package org.example.project.preview.battle

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.param.EXP
import core.domain.status.param.statusParameter.StatusParameter
import core.domain.status.param.statusParameterWithMax.HP
import core.domain.status.param.statusParameterWithMax.MP
import core.domain.status.param.statusParameterWithMax.StatusParameterData
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
                hp = HP(
                    StatusParameterData(
                        maxPoint = 100,
                        point = 50,
                    )
                ),
                mp = MP(
                    StatusParameterData(
                        maxPoint = 10,
                        point = 5,
                    )
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
