package gamescreen.text

import gamescreen.text.repository.TextRepository
import gamescreen.text.repository.TextRepositoryImpl
import org.koin.dsl.module

val ModuleText = module {
    single<TextRepository> {
        TextRepositoryImpl()
    }

    single<TextViewModel> {
        TextViewModel()
    }
}
