package main

import controller.domain.ControllerCallback
import core.repository.screentype.ScreenTypeRepository
import gamescreen.GameScreenType
import gamescreen.battle.BattleViewModel
import gamescreen.choice.Choice
import gamescreen.choice.ChoiceViewModel
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.map.viewmodel.MapViewModel
import gamescreen.menu.MenuViewModel
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.menushop.viewmodel.BuyViewModel
import gamescreen.text.TextBoxData
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

    private val shopMenuRepository: ShopMenuRepository,
    private val buyViewModel: BuyViewModel,

    private val screenTypeRepository: ScreenTypeRepository,
) : KoinComponent {
    private val mutableControllerFlow =
        MutableStateFlow<ControllerCallback>(
            mapViewModel,
        )
    val controllerFlow = mutableControllerFlow.asStateFlow()

    private var choiceList: List<Choice> = emptyList()
    private var textBoxData: TextBoxData? = null
    private var gameScreenType: GameScreenType = GameScreenType.FIELD
    private var shopType: ShopType = ShopType.CLOSE


    init {
        CoroutineScope(Dispatchers.Main).launch {
            textRepository.textDataStateFlow.collect {
                textBoxData = it
                updateScreenType()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            choiceRepository.choiceListStateFlow.collect {
                choiceList = it
                updateScreenType()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            screenTypeRepository.screenStateFlow.collect {
                gameScreenType = it
                updateScreenType()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            shopMenuRepository.shopTypeStateFlow.collect {
                shopType = it
                updateScreenType()
            }
        }
    }

    private fun updateScreenType() {
        if (choiceList.isNotEmpty()) {
            mutableControllerFlow.value = choiceViewModel
            return
        }

        if (textBoxData != null) {
            mutableControllerFlow.value = textViewModel
            return
        }

        if (shopType == ShopType.BUY) {
            mutableControllerFlow.value = buyViewModel
            return
        }

        mutableControllerFlow.value = when (gameScreenType) {
            GameScreenType.BATTLE -> battleViewModel
            GameScreenType.FIELD -> mapViewModel
            GameScreenType.MENU -> menuViewModel
        }
    }
}
