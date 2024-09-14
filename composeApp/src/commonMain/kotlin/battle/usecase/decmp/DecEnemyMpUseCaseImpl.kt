package battle.usecase.decmp

import battle.repository.battlemonster.BattleMonsterRepository
import common.values.playerNum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DecEnemyMpUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
) : DecMpUseCase {

    override fun invoke(
        id: Int,
        amount: Int
    ) {
        val monster = battleMonsterRepository.getMonster(id = id - playerNum)
        val afterMonster = monster.copy(
            mp = monster.mp.copy(
                value = monster.mp.value - amount,
            )
        )
        CoroutineScope(Dispatchers.Default).launch {
            battleMonsterRepository.setMonster(
                id = id - playerNum,
                monster = afterMonster,
            )
        }
    }
}
