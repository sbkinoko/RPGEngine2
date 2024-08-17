package battle.command

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import battle.BattleViewModel
import battle.command.attackphase.AttackPhase
import battle.command.main.MainCommand
import battle.command.playeraction.PlayerAction
import battle.command.selectenemy.SelectEnemy
import battle.domain.AttackPhaseCommand
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand

@Composable
fun CommandArea(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel,
) {
    when (val nowState = battleViewModel.commandState.collectAsState().value.nowState) {
        is MainCommand -> MainCommand(
            modifier = modifier,
            mainCommandCallBack = battleViewModel.mainCommandCallback,
        )

        is PlayerActionCommand -> PlayerAction(
            modifier = modifier,
            playerActionCallBack = battleViewModel.playerCommandCallback,
            playerStatus = battleViewModel.players[nowState.playerId]
        )

        is SelectEnemyCommand -> {
            SelectEnemy(
                modifier = modifier,
                playerStatus = battleViewModel.players[nowState.playerId]
            )
        }

        is AttackPhaseCommand -> {
            AttackPhase(
                modifier = modifier,
                playerName = battleViewModel.players[battleViewModel.attackingPlayerId.collectAsState().value].name,
                targetName = battleViewModel.targetName,
                attackPhaseCallBack = battleViewModel.attackPhaseCommandCallback,
            )
        }
    }
}
