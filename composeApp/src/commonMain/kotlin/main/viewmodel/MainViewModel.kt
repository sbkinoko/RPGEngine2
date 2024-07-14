package main.viewmodel

import main.repository.screentype.ScreenTypeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()
    val nowScreenType = screenTypeRepository.screenTypeFlow
}
