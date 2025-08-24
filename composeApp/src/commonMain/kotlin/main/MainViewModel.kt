package main

import core.repository.character.statusdata.StatusDataRepository
import core.repository.screentype.ScreenTypeRepository
import data.repository.status.StatusRepository
import gamescreen.init.InitUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class MainViewModel(
    val statusDataRepository: StatusDataRepository,
    val statusRepository: StatusRepository,
    val initUseCase: InitUseCase,
) : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val nowScreenType = screenTypeRepository.screenStateFlow

    private val screenTypeManager: ScreenTypeManager by inject()

    val controllerFlow = screenTypeManager.controllerFlow

    init {
        initUseCase.invoke()
        statusDataRepository.setStatusList(
            List(Constants.playerNum) {
                statusRepository.getStatus(
                    id = it,
                    level = 1,
                ).second
            }
        )
    }
}
