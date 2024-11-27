package gamescreen.text

import gamescreen.choice.ChoiceViewModel
import org.koin.dsl.module

val TextModule = module {
    single<ChoiceViewModel> {
        ChoiceViewModel()
    }

    single<TextViewModel> {
        TextViewModel()
    }
}
