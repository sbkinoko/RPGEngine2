package menu

import menu.repository.menustate.MenuStateRepository
import menu.repository.menustate.MenuStateRepositoryImpl
import org.koin.dsl.module

val MenuModule = module {
    single<MenuStateRepository> {
        MenuStateRepositoryImpl()
    }
}
