package gamescreen.battle.command.actionphase

enum class ActionState {
    Start {
        override val next: ActionState
            get() = Paralyze

    },
    Paralyze {
        override val next: ActionState
            get() = Action

    },
    Action {
        override val next: ActionState
            get() = Poison
    },
    Poison {
        override val next: ActionState
            get() = CurePoison
    },
    CurePoison {
        override val next: ActionState
            get() = CureParalyze
    },
    CureParalyze {
        override val next: ActionState
            get() = Next
    },
    Next {
        override val next: ActionState
            get() = Start
    },
    ;

    abstract val next: ActionState
}
