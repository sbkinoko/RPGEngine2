package gamescreen.battle.command.actionphase

import core.domain.status.ConditionType

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

    class Poison(
        var damage: Int = 0,
    ) : ActionState() {
        override val next: ActionState
            get() = CurePoison()
    }

    class CurePoison(
        override var list: List<ConditionType> = emptyList(),
    ) : ActionState(), Cure {
        override val next: ActionState
            get() = CureParalyze()
    }

    class CureParalyze(
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
