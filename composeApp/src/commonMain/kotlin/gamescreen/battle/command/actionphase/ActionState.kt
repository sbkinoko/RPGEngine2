package gamescreen.battle.command.actionphase

import core.domain.status.ConditionType
import core.domain.status.canMove
import core.domain.status.tryCalcPoisonDamage
import core.domain.status.tryCure

sealed class ActionState {
    interface Cure {
        var list: List<ConditionType>
    }

    data object Start : ActionState() {
        override val next: ActionState
            get() = Paralyze
    }

    data object Paralyze : ActionState() {
        override val next: ActionState
            get() = Action

    }

    data object Action : ActionState() {
        override val next: ActionState
            get() = Poison()
    }

    data class Poison(
        var damage: Int = 0,
    ) : ActionState() {
        override val next: ActionState
            get() = CurePoison()
    }

    data class CurePoison(
        override var list: List<ConditionType> = emptyList(),
    ) : ActionState(), Cure {
        override val next: ActionState
            get() = CureParalyze()
    }

    data class CureParalyze(
        override var list: List<ConditionType> = emptyList(),
    ) : ActionState(), Cure {
        override val next: ActionState
            get() = Next
    }

    data object Next : ActionState() {
        override val next: ActionState
            get() = Start
    }


    abstract val next: ActionState
}


/**
 * 現在のアクションの状態と状態異常から次のアクションの状態を決定する
 */
fun ActionState.getNextState(
    conditionList: List<ConditionType>
): ActionState {
    // fixme 初期状態を保持するようにしたい
    var tmpState = this

    while (true) {
        tmpState = tmpState.next
        when (tmpState) {
            // 初期状態で固定されることはない
            ActionState.Start -> continue

            ActionState.Paralyze -> {
                val paralyzeList = conditionList
                    .filterIsInstance<ConditionType.Paralysis>()

                if (paralyzeList.isEmpty()) {
                    // 麻痺に関する処理はないので処理を続ける
                    continue
                }

                val canMove = paralyzeList.canMove()

                if (!canMove) {
                    // 動けないので状態が確定
                    return tmpState
                }

                //　動けるので次のstateで処理を続ける
                continue
            }

            ActionState.Action -> {
                // actionで状態を固定
                return tmpState
            }

            is ActionState.Poison -> {
                val damage = conditionList.tryCalcPoisonDamage()

                if (damage == 0) {
                    continue
                }

                // 毒でダメージを受けたので状態を固定
                return ActionState.Poison(
                    damage = damage
                )
            }

            is ActionState.CurePoison -> {
                // fixme 改行位置修正
                val after = conditionList
                    .tryCure<ConditionType.Poison>()

                // 毒を直す処理はないので次を探す
                if (conditionList == after) {
                    continue
                }

                // 毒を直す処理で固定
                return ActionState.CurePoison(
                    list = after,
                )
            }

            is ActionState.CureParalyze -> {
                val after = conditionList.tryCure<
                        ConditionType.Paralysis>()

                // 麻痺を直す処理はないので次を探す
                if (conditionList == after) {
                    continue
                }

                // 麻痺を直す処理で固定
                return ActionState.CureParalyze(
                    list = after,
                )
            }

            ActionState.Next -> {
                // nextで状態を固定
                return tmpState
            }
        }
    }
}
