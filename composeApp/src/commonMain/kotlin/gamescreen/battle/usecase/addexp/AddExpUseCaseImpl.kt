package gamescreen.battle.usecase.addexp

import common.DefaultScope
import core.repository.memory.character.player.PlayerCharacterRepository
import data.repository.status.StatusRepository
import kotlinx.coroutines.launch

class AddExpUseCaseImpl(
    private val playerStatusRepository: PlayerCharacterRepository,
    private val statusRepository: StatusRepository,

    private val statusDataRepository: core.repository.memory.character.statusdata.StatusDataRepository,
) : AddExpUseCase {
    override fun invoke(exp: Int): List<String> {
        val levelUpList: MutableList<String> = mutableListOf()
        val afterList = playerStatusRepository.getStatusList().toMutableList()
        for (index: Int in 0 until playerStatusRepository.getStatusList().size) {
            val playerStatus = playerStatusRepository.getStatus(index)

            //経験値の加算
            var after = playerStatus.copy(
                exp = playerStatus.exp.copy(
                    value = playerStatus.exp.value + exp
                )
            )

            val nowStatus = statusDataRepository.getStatusData(id = index)

            var afterStatus = statusDataRepository.getStatusData(id = index)

            // レベルが上がっていたら
            if (playerStatus.exp.level != after.exp.level) {
                //表示用リストに追加
                levelUpList.add(nowStatus.name)

                //上がったステータスを取得して
                statusRepository.getStatus(
                    id = index,
                    level = after.exp.level
                ).apply {
                    //ステータスに反映する
                    after = this.first

                    afterStatus = this.second.setHP(
                        value = nowStatus.hp.point,
                    ).setMP(
                        value = nowStatus.mp.point,
                    )
                }
            }

            DefaultScope.launch {
                //保存
                afterList[index] = after
                statusDataRepository.setStatusData(
                    id = index,
                    statusData = afterStatus,
                )
            }
        }

        DefaultScope.launch {
            playerStatusRepository.setStatusList(afterList)
        }


        return levelUpList
    }
}
