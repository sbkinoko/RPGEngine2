package data.event

import data.event.battle.BattleEventKey
import data.event.battle.EventManagerImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val QualifierEventBattle = named("EventBattle")

val ModuleEvent = module {

    single<EventManager<BattleEventKey>>(
        qualifier = QualifierEventBattle,
    ) {
        EventManagerImpl(
            maxHealUseCase = get(),
            textRepository = get(),
        )
    }
}
