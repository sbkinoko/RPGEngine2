package main

import core.repository.memory.screentype.ScreenTypeRepository
import gamescreen.init.InitUseCase
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel(
    val initUseCase: InitUseCase,
) : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val nowScreenType = screenTypeRepository.screenStateFlow

    private val screenTypeManager: ScreenTypeManager by inject()

    val controllerFlow = screenTypeManager.controllerFlow

    init {
        runBlocking {
            initUseCase.invoke()
        }
    }
}
