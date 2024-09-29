package battle.command

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import battle.BattleViewModel
import battle.command.actionphase.ActionPhase
import battle.command.escape.EscapeCommand
import battle.command.finish.FinishCommandWindow
import battle.command.main.BattleMainCommand
import battle.command.playeraction.PlayerAction
import battle.command.selectally.SelectAllyCommandWindow
import battle.command.selectenemy.SelectEnemy
import battle.command.skill.SkillCommandWindow
import battle.domain.AttackPhaseCommand
import battle.domain.EscapeCommand
import battle.domain.FinishCommand
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectAllyCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
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

        is FinishCommand -> {
            FinishCommandWindow()
        }
    }
}
