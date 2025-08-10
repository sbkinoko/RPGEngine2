package gamescreen.battle.usecase.gettargetnum

import core.domain.item.NeedTarget
import data.repository.item.skill.SkillRepository
import gamescreen.battle.domain.ActionType
import gamescreen.battle.repository.action.ActionRepository

class GetTargetNumUseCaseImpl(
    private val actionRepository: ActionRepository,
    private val skillRepository: SkillRepository,
) : GetTargetNumUseCase {
    override fun invoke(playerId: Int): Int {
        return actionRepository.getAction(playerId).let {
            when (it.thisTurnAction) {
                ActionType.Normal -> {
                    //todo 装備の種類によって攻撃対象の数を変えられるようにする
                    1
                }

                ActionType.Skill -> {
                    val skill = skillRepository.getItem(it.skillId)
                    (skill as NeedTarget).targetNum
                }

                ActionType.TOOL -> {
                    TODO("敵を攻撃する道具を作ったら実装する")
                }

                ActionType.None -> {
                    throw IllegalStateException("Noneで敵の対象数を取得することはない")
                }
            }
        }
    }
}
