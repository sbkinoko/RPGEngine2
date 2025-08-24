package gamescreen.map.usecase.battlestart

import common.DefaultScope
import core.domain.BattleEventCallback
import core.domain.BattleResult
import core.domain.status.StatusData
import core.domain.status.monster.MonsterStatus
import core.repository.memory.character.battlemonster.BattleInfoRepository
import core.repository.memory.screentype.ScreenTypeRepository
import gamescreen.GameScreenType
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import gamescreen.battle.repository.flash.FlashRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class StartBattleUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
    private val screenTypeRepository: ScreenTypeRepository,
    private val commandStateRepository: CommandStateRepository,
    private val actionRepository: ActionRepository,
    private val eventRepository: core.repository.memory.event.EventRepository,
    private val flashRepository: FlashRepository,
    private val attackEffectRepository: AttackEffectRepository,

    private val statusDataRepository: core.repository.memory.character.statusdata.StatusDataRepository,
) : StartBattleUseCase, KoinComponent {

    override operator fun invoke(
        monsterList: List<Pair<MonsterStatus, StatusData>>,
        battleEventCallback: BattleEventCallback,
        backgroundType: BattleBackgroundType,
    ) {
        if (monsterList.isEmpty()) {
            return
        }

        DefaultScope.launch {
            battleInfoRepository.setMonsters(
                monsterList.map { it.first }
            )

            statusDataRepository.setStatusList(
                renameMonster(monsterList.map { it.second })
            )

            flashRepository.monsterNum = monsterList.size
            attackEffectRepository.monsterNum = monsterList.size

            battleInfoRepository.setBackgroundType(
                backgroundType
            )
            screenTypeRepository.setScreenType(
                gameScreenType = GameScreenType.BATTLE,
            )
            commandStateRepository.init()
            actionRepository.resetTarget()
            eventRepository.setResult(BattleResult.None)
            eventRepository.setCallBack(
                battleEventCallback,
            )
        }
    }

    // TODO: test作る
    private fun renameMonster(monsters: List<StatusData>): List<StatusData> {
        val nameNum: MutableMap<String, Int> = mutableMapOf()
        monsters.map {
            it.let {
                if (nameNum[it.name] != null) {
                    nameNum[it.name] = (nameNum[it.name]!! + 1)
                } else {
                    nameNum[it.name] = 1
                }
            }
        }

        val nameId: MutableMap<String, Int> = mutableMapOf()

        return monsters.map {
            if (nameNum[it.name] == 1) {
                return@map it
            }

            if (nameId[it.name] != null) {
                nameId[it.name] = (nameId[it.name]!! + 1)
            } else {
                nameId[it.name] = 1
            }


            it.copy(
                name = it.name + nameId[it.name]
            )
        }
    }
}
