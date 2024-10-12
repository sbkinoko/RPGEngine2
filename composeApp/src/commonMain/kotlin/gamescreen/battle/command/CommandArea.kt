package gamescreen.battle.command

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.battle.BattleViewModel
import gamescreen.battle.command.actionphase.ActionPhase
import gamescreen.battle.command.escape.EscapeCommand
import gamescreen.battle.command.finish.FinishCommandWindow
import gamescreen.battle.command.item.skill.SkillCommandWindow
import gamescreen.battle.command.item.tool.ToolCommandWindow
import gamescreen.battle.command.main.BattleMainCommand
import gamescreen.battle.command.playeraction.PlayerAction
import gamescreen.battle.command.selectally.SelectAllyCommandWindow
import gamescreen.battle.command.selectenemy.SelectEnemy
import gamescreen.battle.domain.AttackPhaseCommand
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.FinishCommand
import gamescreen.battle.domain.MainCommand
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.domain.SkillCommand
import gamescreen.battle.domain.ToolCommand
import org.koin.compose.koinInject

@Composable
fun CommandArea(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel = koinInject(),
) {
    when (val nowState = battleViewModel.CommandStateFlow().value) {
        is MainCommand -> BattleMainCommand(
            modifier = modifier,
        )

        is PlayerActionCommand -> PlayerAction(
            modifier = modifier,
            playerStatus = battleViewModel.players[nowState.playerId]
        )

        is SelectEnemyCommand -> {
            SelectEnemy(
                modifier = modifier,
                playerStatus = battleViewModel.players[nowState.playerId]
            )
        }

        is SelectAllyCommand -> {
            SelectAllyCommandWindow(
                modifier = modifier,
                playerStatus = battleViewModel.players[nowState.playerId]
            )
        }

        is AttackPhaseCommand -> {
            ActionPhase(
                modifier = modifier,
            )
        }

        is EscapeCommand -> {
            EscapeCommand(
                modifier = modifier,
            )
        }

        is SkillCommand -> {
            SkillCommandWindow(
                modifier = modifier,
            )
        }

        is ToolCommand -> {
            ToolCommandWindow(
                modifier = modifier,
            )
        }

        is FinishCommand -> {
            FinishCommandWindow()
        }
    }
}
