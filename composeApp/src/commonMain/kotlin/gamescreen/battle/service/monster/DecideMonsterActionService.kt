package gamescreen.battle.service.monster

import core.domain.status.monster.ActionStyle
import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.ActionData
import gamescreen.battle.domain.ActionType
import kotlin.random.Random

class DecideMonsterActionService {

    fun getAction(
        monster: MonsterStatus,
    ): ActionData {
        return when (monster.actionStyle) {
            ActionStyle.RANDOM -> {
                random(monster)
            }
        }
    }

    private fun random(
        monsterStatus: MonsterStatus,
    ): ActionData {
        val rnd = Random.nextInt(monsterStatus.skillList.size)
        val skillId = monsterStatus.skillList[rnd]

        return ActionData(
            thisTurnAction = ActionType.Skill,
            skillId = skillId,
            target = 0,
            ally = 0,
            toolId = 0,
            toolIndex = 0,
        )
    }
}
