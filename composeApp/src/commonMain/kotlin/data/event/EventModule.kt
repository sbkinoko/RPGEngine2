package data.event

import data.event.battle.BattleEventManager1
import data.event.talk.TalkEventManager1
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

}
