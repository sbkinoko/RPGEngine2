package gamescreen.battle.usecase.addexp

import core.repository.player.PlayerStatusRepository
import data.status.StatusRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddExpUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val statusRepository: StatusRepository,
) : AddExpUseCase {
    override fun invoke(exp: Int): List<String> {
        val levelUpList: MutableList<String> = mutableListOf()
        playerStatusRepository.getPlayers().mapIndexed { index, it ->
            //経験値の加算
            var after = it.copy(
                exp = it.exp.copy(
                    value = it.exp.value + exp
                )
            )

            // レベルが上がっていたら
            if (it.exp.level != after.exp.level) {
                //表示用リストに追加
                levelUpList.add(it.statusData.name)

                //上がったステータスを取得して
                statusRepository.getStatus(
                    id = index,
                    level = after.exp.level
                ).apply {
                    //ステータスに反映する
                    after = after.copy(
                        statusData = statusData.copy(
                            hp = after.statusData.hp.copy(
                                maxValue = statusData.hp.maxValue
                            ),
                            mp = after.statusData.mp.copy(
                                maxValue = statusData.mp.maxValue
                            ),
                        ),
                    )
                }
            }

            CoroutineScope(Dispatchers.Default).launch {
                //保存
                playerStatusRepository.setStatus(
                    id = index,
                    status = after,
                )
            }
        }

        return levelUpList
    }
}
