package gamescreen.text

import gamescreen.choice.ChoiceViewModel
import org.koin.dsl.module

val ModuleText = module {
    single<ChoiceViewModel> {
        ChoiceViewModel()
    }

    single<TextViewModel> {
        TextViewModel()
    }
}
