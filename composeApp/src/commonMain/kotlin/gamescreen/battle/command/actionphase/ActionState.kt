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
            get() = Next
    },
    Next {
        override val next: ActionState
            get() = Start
    },
    ;

    abstract val next: ActionState
}
