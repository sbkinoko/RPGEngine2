package main

import controller.domain.ControllerCallback
import core.domain.ScreenType
import core.repository.screentype.ScreenTypeRepository
import gamescreen.battle.BattleViewModel
import gamescreen.choice.ChoiceViewModel
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.map.viewmodel.MapViewModel
import gamescreen.menu.MenuViewModel
import gamescreen.text.TextViewModel
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ScreenTypeManager(
    private val mapViewModel: MapViewModel,
    private val battleViewModel: BattleViewModel,
    private val menuViewModel: MenuViewModel,

    private val choiceRepository: ChoiceRepository,
    private val choiceViewModel: ChoiceViewModel,

    private val textRepository: TextRepository,
    private val textViewModel: TextViewModel,

    private val screenTypeRepository: ScreenTypeRepository,
) : KoinComponent {
    private val mutableControllerFlow =
        MutableStateFlow<ControllerCallback>(
            mapViewModel,
        )
    val controllerFlow = mutableControllerFlow.asStateFlow()


    init {
        CoroutineScope(Dispatchers.Main).launch {
            textRepository.commandTypeFlow.collect {
                updateScreenType()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            choiceRepository.commandStateFlow.collect {
                updateScreenType()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            screenTypeRepository.screenTypeFlow.collect {
                updateScreenType()
            }
        }
    }

    private fun updateScreenType() {
        if (choiceRepository.nowCommandType.isNotEmpty()) {
            mutableControllerFlow.value = choiceViewModel
            return
        }

        if (textRepository.nowCommandType != null) {
            mutableControllerFlow.value = textViewModel
            return
        }

        mutableControllerFlow.value = when (screenTypeRepository.screenType) {
            ScreenType.BATTLE -> battleViewModel
            ScreenType.FIELD -> mapViewModel
            ScreenType.MENU -> menuViewModel
        }
    }
}
