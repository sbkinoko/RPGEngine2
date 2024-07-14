package main

import main.repository.screentype.ScreenTypeRepository
import main.repository.screentype.ScreenTypeRepositoryImpl
import org.koin.dsl.module

val MainModule = module {
    single<ScreenTypeRepository> {
        ScreenTypeRepositoryImpl()
    }
}
