package data.event

import data.event.battle.BattleEventKey
import data.event.battle.EventBattleManager1
import org.koin.core.qualifier.named
import org.koin.dsl.module

val QualifierEventBattle = named("EventBattle")

val ModuleEvent = module {

    single<EventManager<BattleEventKey>>(
        qualifier = QualifierEventBattle,
    ) {
        EventBattleManager1(
            maxHealUseCase = get(),
            textRepository = get(),

            startBattleUseCase = get(),
            monsterRepository = get(),
        )
    }
}
