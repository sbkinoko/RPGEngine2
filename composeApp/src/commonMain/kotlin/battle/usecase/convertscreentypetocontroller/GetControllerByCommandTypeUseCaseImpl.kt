package battle.usecase.convertscreentypetocontroller

import battle.command.actionphase.ActionPhaseViewModel
import battle.command.escape.EscapeViewModel
import battle.command.main.BattleMainViewModel
import battle.command.playeraction.PlayerActionViewModel
import battle.command.selectally.SelectAllyViewModel
import battle.command.selectenemy.SelectEnemyViewModel
import battle.command.skill.SkillCommandViewModel
import battle.domain.AttackPhaseCommand
import battle.domain.EscapeCommand
import battle.domain.FinishCommand
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectAllyCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.commandstate.CommandStateRepository
import controller.domain.ControllerCallback

class GetControllerByCommandTypeUseCaseImpl(
    private val commandStateRepository: CommandStateRepository,

    private val battleMainViewModel: BattleMainViewModel,
    private val playerActionViewModel: PlayerActionViewModel,
    private val selectEnemyViewModel: SelectEnemyViewModel,
    private val actionPhaseViewModel: ActionPhaseViewModel,
    private val escapeViewModel: EscapeViewModel,
    private val skillCommandViewModel: SkillCommandViewModel,
    private val selectAllyViewModel: SelectAllyViewModel,
) : GetControllerByCommandTypeUseCase {
    override operator fun invoke(): ControllerCallback? {
        return when (commandStateRepository.nowCommandType) {
            is MainCommand -> battleMainViewModel
            is PlayerActionCommand -> playerActionViewModel
            is SelectEnemyCommand -> selectEnemyViewModel
            is SelectAllyCommand -> selectAllyViewModel
            is AttackPhaseCommand -> actionPhaseViewModel
            is EscapeCommand -> escapeViewModel
            is FinishCommand -> null
            is SkillCommand -> skillCommandViewModel
        }
    }
}
