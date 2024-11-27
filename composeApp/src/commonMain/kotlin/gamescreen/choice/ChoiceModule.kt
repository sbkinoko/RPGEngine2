package gamescreen.choice

import gamescreen.choice.repository.ChoiceRepository
import gamescreen.choice.repository.ChoiceRepositoryImpl
import gamescreen.text.repository.TextRepository
import gamescreen.text.repository.TextRepositoryImpl
import org.koin.dsl.module

val ChoiceModule = module {
    single<ChoiceRepository> {
        ChoiceRepositoryImpl()
    }

    single<TextRepository> {
        TextRepositoryImpl()
    }
}
