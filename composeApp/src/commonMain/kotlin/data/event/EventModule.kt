package data.event

import org.koin.dsl.module

val ModuleEvent = module {

    single<BattleEventManager1> {
        BattleEventManager1(
            maxHealUseCase = get(),
            textRepository = get(),

            startBattleUseCase = get(),
            monsterRepository = get(),
        )
    }

    single<TalkEventManager1> {
        TalkEventManager1(
            textRepository = get(),
            choiceRepository = get(),
        )
    }

    single<TalkEventManager2> {
        TalkEventManager2(
            textRepository = get(),
        )
    }

}
