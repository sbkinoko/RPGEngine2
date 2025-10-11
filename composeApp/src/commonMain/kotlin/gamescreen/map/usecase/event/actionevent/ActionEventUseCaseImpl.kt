package gamescreen.map.usecase.event.actionevent

import common.DefaultScope
import core.domain.mapcell.CellType
import data.repository.item.tool.ToolId
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.ObjectHeightDetail
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.service.makefrontdata.MakeFrontDateService
import gamescreen.map.usecase.changeheight.ChangeHeightUseCase
import gamescreen.map.usecase.movetootherheight.MoveToOtherHeightUseCase
import gamescreen.map.usecase.settalk.SetTalkUseCase
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.menushop.usecase.setshopitem.SetShopItemUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.launch
import values.event.BoxData
import values.event.EventType

// todo 画面に反映できるようにする
class ActionEventUseCaseImpl(
    private val textRepository: TextRepository,
    private val addToolUseCase: AddToolUseCase<ToolId>,
    private val setShopItemUseCase: SetShopItemUseCase,
    private val setTalkUseCase: SetTalkUseCase,
    private val moveToOtherHeightUseCase: MoveToOtherHeightUseCase,
    private val changeHeightUseCase: ChangeHeightUseCase,
    private val makeFrontDateService: MakeFrontDateService,

    private val choiceRepository: ChoiceRepository,
    private val shopMenuRepository: ShopMenuRepository,
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

            is EventType.TalkEvent -> {
                setTalkUseCase.invoke(
                    talkEvent = eventType.talkID,
                )
            }

            is EventType.Shop -> {
                val textBoxData = listOf(
                    TextBoxData(
                        text = "いらっしゃい",
                        callBack = {
                            choiceRepository.push(
                                listOf(
                                    Choice(
                                        "買う",
                                        callBack = {
                                            setShopItemUseCase.invoke(eventType.shopId)

                                            shopMenuRepository.setShopType(ShopType.BUY)
                                        }
                                    ),
                                    Choice(
                                        "売る",
                                        callBack = {
                                            shopMenuRepository.setShopType(
                                                ShopType.SELL
                                            )
                                        }
                                    ),
                                    Choice(
                                        "何もしない",
                                        callBack = {
                                            textRepository.push(
                                                TextBoxData(
                                                    text = "また来てね",
                                                )
                                            )
                                        },
                                    ),
                                )
                            )
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
                DefaultScope.launch {
                    moveToOtherHeightUseCase.invoke(
                        targetHeight = ObjectHeight.Water(ObjectHeightDetail.Mid),
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
                DefaultScope.launch {
                    moveToOtherHeightUseCase.invoke(
                        targetHeight = ObjectHeight.Ground(ObjectHeightDetail.Mid),
                        mapUiState = mapUiState,
                        update = update,
                    )
                }
            }

            EventType.Ground1 -> {
                DefaultScope.launch {
                    update(
                        mapUiState.copy(
                            player = changeHeightUseCase.invoke(
                                ObjectHeight.Ground(ObjectHeightDetail.Mid),
                                mapUiState.player,
                            )
                        )
                    )
                }
            }

            EventType.Ground2 -> {
                DefaultScope.launch {
                    update(
                        mapUiState.copy(
                            player = changeHeightUseCase.invoke(
                                ObjectHeight.Ground(ObjectHeightDetail.High),
                                mapUiState.player,
                            )
                        )
                    )
                }
            }
        }
    }
}
