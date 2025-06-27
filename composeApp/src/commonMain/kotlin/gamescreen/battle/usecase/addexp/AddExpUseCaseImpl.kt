package gamescreen.battle.usecase.addexp

import common.DefaultScope
import core.domain.status.StatusType
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.status.StatusRepository
import kotlinx.coroutines.launch

class AddExpUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val statusRepository: StatusRepository,

    private val statusDataRepository: StatusDataRepository<StatusType.Player>,
) : AddExpUseCase {
    override fun invoke(exp: Int): List<String> {
        val levelUpList: MutableList<String> = mutableListOf()
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
                playerStatusRepository.setStatus(
                    id = index,
                    status = after,
                )
                statusDataRepository.setStatusData(
                    id = index,
                    statusData = afterStatus,
                )
            }
        }

        return levelUpList
    }
}
