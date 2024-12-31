package gamescreen.battle.usecase.getcontrollerbyscreentype

import controller.domain.ControllerCallback
import gamescreen.battle.command.actionphase.ActionPhaseViewModel
import gamescreen.battle.command.escape.EscapeViewModel
import gamescreen.battle.command.finish.BattleFinishViewModel
import gamescreen.battle.command.item.skill.SkillCommandViewModel
import gamescreen.battle.command.item.tool.ToolCommandViewModel
import gamescreen.battle.command.main.BattleMainViewModel
import gamescreen.battle.command.playeraction.PlayerActionViewModel
import gamescreen.battle.command.selectally.SelectAllyViewModel
import gamescreen.battle.command.selectenemy.SelectEnemyViewModel
import gamescreen.battle.domain.AttackPhaseCommand
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.FinishCommand
import gamescreen.battle.domain.MainCommand
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.domain.SkillCommand
import gamescreen.battle.domain.ToolCommand
import gamescreen.battle.repository.commandstate.CommandStateRepository

class GetControllerByCommandTypeUseCaseImpl(
    private val commandStateRepository: CommandStateRepository,

    private val battleMainViewModel: BattleMainViewModel,
    private val playerActionViewModel: PlayerActionViewModel,
    private val selectEnemyViewModel: SelectEnemyViewModel,
    private val actionPhaseViewModel: ActionPhaseViewModel,
    private val escapeViewModel: EscapeViewModel,
    private val skillCommandViewModel: SkillCommandViewModel,
    private val toolCommandViewModel: ToolCommandViewModel,
    private val selectAllyViewModel: SelectAllyViewModel,

    private val battleFinishViewModel: BattleFinishViewModel,
) : GetControllerByCommandTypeUseCase {
    override operator fun invoke(): ControllerCallback {
        return when (commandStateRepository.nowBattleCommandType) {
            is MainCommand -> battleMainViewModel
            is PlayerActionCommand -> playerActionViewModel
            is SelectEnemyCommand -> selectEnemyViewModel
            is SelectAllyCommand -> selectAllyViewModel
            is AttackPhaseCommand -> actionPhaseViewModel
            is EscapeCommand -> escapeViewModel
            is FinishCommand -> battleFinishViewModel
            is SkillCommand -> skillCommandViewModel
            is ToolCommand -> toolCommandViewModel
        }
    }
}
