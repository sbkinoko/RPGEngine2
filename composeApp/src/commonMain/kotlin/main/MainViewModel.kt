package main

import core.repository.screentype.ScreenTypeRepository
import core.repository.statusdata.StatusDataRepository
import data.repository.status.StatusRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class MainViewModel(
    val statusDataRepository: StatusDataRepository,
    val statusRepository: StatusRepository,
) : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val nowScreenType = screenTypeRepository.screenStateFlow

    private val screenTypeManager: ScreenTypeManager by inject()

    val controllerFlow = screenTypeManager.controllerFlow

    init {
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
