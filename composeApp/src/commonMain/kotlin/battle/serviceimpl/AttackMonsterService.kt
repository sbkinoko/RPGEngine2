package battle.serviceimpl

import battle.service.AttackService
import common.status.MonsterStatus

class AttackMonsterService : AttackService {

    override fun attack(
        target: Int,
        damage: Int,
        monsters: List<MonsterStatus>,
    ): List<MonsterStatus> {
        return monsters
            //　ダメージを与えた敵だけ新しいインスタンスに変更
            .mapIndexed { index, monsterStatus ->
                if (index != target) {
                    monsterStatus
                } else {
                    monsterStatus.copy(
                        hp = monsterStatus.hp.copy(
                            value = monsterStatus.hp.value - damage
                        )
                    )
                }
            }
    }
}
