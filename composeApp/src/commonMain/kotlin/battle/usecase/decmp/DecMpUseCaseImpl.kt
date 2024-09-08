package battle.usecase.decmp

import battle.repository.battlemonster.BattleMonsterRepository
import common.repository.player.PlayerRepository
import common.values.playerNum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DecMpUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val battleMonsterRepository: BattleMonsterRepository,
) : DecMpUseCase {

    override fun invoke(
        playerId: Int,
        amount: Int
    ) {
        if (playerId < playerNum) {
            val player = playerRepository.getPlayer(id = playerId)
            val afterPlayer = player.copy(
                mp = player.mp.copy(
                    value = player.mp.value - amount,
                )
            )
            playerRepository.setPlayer(
                id = playerId,
                status = afterPlayer,
            )
        } else {
            val monster = battleMonsterRepository.getMonster(id = playerId - playerNum)
            val afterMonster = monster.copy(
                mp = monster.mp.copy(
                    value = monster.mp.value - amount,
                )
            )
            CoroutineScope(Dispatchers.Default).launch {
                battleMonsterRepository.setMonster(
                    id = playerId - playerNum,
                    monster = afterMonster,
                )
            }
        }
    }
}
