package battle.command

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import battle.BattleViewModel
import battle.command.actionphase.ActionPhase
import battle.command.main.MainCommand
import battle.command.playeraction.PlayerAction
import battle.command.selectenemy.SelectEnemy
import battle.domain.AttackPhaseCommand
import battle.domain.FinishCommand
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand

@Composable
fun CommandArea(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel,
) {
    when (val nowState = battleViewModel.CommandStateFlow().value) {
        is MainCommand -> MainCommand(
            modifier = modifier,
            mainViewModel = battleViewModel.mainViewModel,
        )

        is PlayerActionCommand -> PlayerAction(
            modifier = modifier,
            playerActionViewModel = battleViewModel.playerActionViewModel,
            playerStatus = battleViewModel.players[nowState.playerId]
        )

        is SelectEnemyCommand -> {
            LaunchedEffect(Unit) {
                battleViewModel.selectEnemyViewModel.updateArrow()
            }
            SelectEnemy(
                modifier = modifier,
                playerStatus = battleViewModel.players[nowState.playerId]
            )
        }

        is AttackPhaseCommand -> {
            ActionPhase(
                modifier = modifier,
                actionPhaseViewModel = battleViewModel.actionPhaseViewModel
            )
        }

        is FinishCommand -> {
            battleViewModel.finishBattle()
        }
    }
}
