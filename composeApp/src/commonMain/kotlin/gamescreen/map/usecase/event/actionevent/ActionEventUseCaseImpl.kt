package gamescreen.map.usecase.event.actionevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.background.BackgroundData
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

// todo 画面に反映できるようにする
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
        mapUiState: MapUiState,
        update: (MapUiState) -> Unit,
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

                val fieldData = mapUiState.backgroundData.fieldData.map { list ->
                    list.map { cell ->
                        if (cell.cellType is CellType.Box &&
                            cell.cellType.id == eventType.id
                        ) {
                            val updated = cell.cellType.copy(
                                hasItem = false,
                            )
                            val newAround = cell.aroundCellId.map {
                                it.toMutableList()
                            }.toMutableList()

                            newAround[1][1] = updated

                            cell.copy(
                                cellType = updated,
                                aroundCellId = newAround,
                            )
                        } else {
                            cell
                        }
                    }
                }

                val backgroundData = BackgroundData(
                    fieldData = fieldData,
                )

                val objectData = makeFrontDateService(
                    backgroundData = backgroundData,
                    player = mapUiState.player,
                )

                update(
                    mapUiState.copy(
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
                        mapUiState = mapUiState,
                        update = update,
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
                        mapUiState = mapUiState,
                        update = update,
                    )
                }
            }

            EventType.Ground1 -> {
                CoroutineScope(Dispatchers.Default).launch {
                    update(
                        mapUiState.copy(
                            player = changeHeightUseCase.invoke(
                                ObjectHeight.Ground(1),
                                mapUiState.player,
                            )
                        )
                    )
                }
            }

            EventType.Ground2 -> {
                CoroutineScope(Dispatchers.Default).launch {
                    update(
                        mapUiState.copy(
                            player = changeHeightUseCase.invoke(
                                ObjectHeight.Ground(2),
                                mapUiState.player,
                            )
                        )
                    )
                }
            }
        }
    }
}
