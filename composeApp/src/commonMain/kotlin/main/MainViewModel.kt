package main

import core.repository.screentype.ScreenTypeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val nowScreenType = screenTypeRepository.screenTypeFlow

    private val screenTypeManager: ScreenTypeManager by inject()

    val controllerFlow = screenTypeManager.controllerFlow
}
