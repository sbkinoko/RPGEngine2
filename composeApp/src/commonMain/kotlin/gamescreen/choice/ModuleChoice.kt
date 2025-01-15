package gamescreen.choice

import gamescreen.choice.repository.ChoiceRepository
import gamescreen.choice.repository.ChoiceRepositoryImpl
import org.koin.dsl.module

val ModuleChoice = module {
    single<ChoiceRepository> {
        ChoiceRepositoryImpl()
    }

    single<ChoiceViewModel> {
        ChoiceViewModel()
    }
}
