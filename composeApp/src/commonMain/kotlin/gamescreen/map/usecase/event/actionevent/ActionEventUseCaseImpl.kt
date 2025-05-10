package gamescreen.map.usecase.event.actionevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.service.makefrontdata.MakeFrontDateService
import gamescreen.map.usecase.changeheight.ChangeHeightUseCase
import gamescreen.map.usecase.movetootherheight.MoveToOtherHeightUseCase
import gamescreen.map.usecase.settalk.SetTalkUseCase
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menushop.usecase.setshopitem.SetShopItemUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import values.event.BoxData
import values.event.EventType
import values.event.TalkEvent

class ActionEventUseCaseImpl(
    private val textRepository: TextRepository,
    private val addToolUseCase: AddToolUseCase,
    private val setShopItemUseCase: SetShopItemUseCase,
    private val setTalkUseCase: SetTalkUseCase,
    private val moveToOtherHeightUseCase: MoveToOtherHeightUseCase,
    private val changeHeightUseCase: ChangeHeightUseCase,
    private val makeFrontDateService: MakeFrontDateService,
) : ActionEventUseCase {

    override fun invoke(
        eventType: EventType,
        uiState: MapUiState,
        callback: (MapUiState) -> Unit,
    ) {
        when (eventType) {
            EventType.None -> Unit
            is EventType.Box -> {
                textRepository.push(
                    TextBoxData(
                        text = "宝箱を開けた",
                    )
                )
                addToolUseCase.invoke(
                    toolNum = 1,
                    toolId = BoxData.getItem(
                        id = eventType.id,
                    ),
                )

                val backgroundData = uiState.backgroundData.copy(
                    uiState.backgroundData.fieldData.map {
                        it.map {
                            if (it.cellType is CellType.Box &&
                                it.cellType.id == eventType.id
                            ) {
                                it.copy(
                                    cellType = it.cellType.copy(
                                        hasItem = false,
                                    )
                                )
                            } else {
                                it
                            }
                        }
                    }
                )

                val objectData = makeFrontDateService(
                    backgroundData = backgroundData,
                    player = uiState.player,
                )

                callback(
                    uiState.copy(
                        backgroundData = backgroundData,
                        backObjectData = objectData.second,
                        frontObjectData = objectData.first,
                    )
                )
            }

            is TalkEvent -> {
                setTalkUseCase.invoke(
                    talkEvent = eventType,
                )
            }

            is EventType.Shop -> {
                val textBoxData = listOf(
                    TextBoxData(
                        text = "いらっしゃい",
                        callBack = {
                            setShopItemUseCase.invoke(eventType.shopId)
                        }
                    )
                )

                textRepository.push(
                    textBoxDataList = textBoxData,
                )
            }

            EventType.Water -> {
                val textBoxData = TextBoxData(
                    text = "水上に出るイベント"
                )
                textRepository.push(
                    textBoxData,
                )
                CoroutineScope(Dispatchers.Default).launch {
                    moveToOtherHeightUseCase.invoke(
                        targetHeight = ObjectHeight.Water(1),
                    )
                }
            }

            EventType.Ground -> {
                val textBoxData = TextBoxData(
                    text = "陸上に出るイベント"
                )
                textRepository.push(
                    textBoxData,
                )
                CoroutineScope(Dispatchers.Default).launch {
                    moveToOtherHeightUseCase.invoke(
                        targetHeight = ObjectHeight.Ground(1),
                    )
                }
            }

            EventType.Ground1 -> {
                CoroutineScope(Dispatchers.Default).launch {
                    changeHeightUseCase.invoke(ObjectHeight.Ground(1))
                }
            }

            EventType.Ground2 -> {
                CoroutineScope(Dispatchers.Default).launch {
                    changeHeightUseCase.invoke(ObjectHeight.Ground(2))
                }
            }
        }
    }
}
