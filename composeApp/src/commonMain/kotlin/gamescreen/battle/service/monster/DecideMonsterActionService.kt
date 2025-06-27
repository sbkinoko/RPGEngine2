package gamescreen.battle.service.monster

import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.monster.ActionStyle
import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.ActionData
import gamescreen.battle.domain.ActionType
import kotlin.random.Random

class DecideMonsterActionService {

    fun getAction(
        monster: MonsterStatus,
        statusData: StatusData,
        playerStatusList: List<PlayerStatus>,
    ): ActionData {
        if (statusData.isActive.not()) {
            return ActionData(
                ActionType.None,
            )
        }

        return when (monster.actionStyle) {
            ActionStyle.RANDOM -> {
                random(
                    monster,
                    playerStatusList.size,
                )
            }
        }
    }

    // todo 敵の回復技を作ったら対象選択方法を考える
    private fun random(
        monsterStatus: MonsterStatus,
        playerNum: Int,
    ): ActionData {
        val rnd = Random.nextInt(monsterStatus.skillList.size)
        val skillId = monsterStatus.skillList[rnd]

        val target = Random.nextInt(playerNum)

        return ActionData.default().copy(
            thisTurnAction = ActionType.Skill,
            skillId = skillId,
            target = target,
            ally = 0,
        )
    }
}
