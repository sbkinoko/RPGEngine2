package gamescreen.battle.command.finish

import core.repository.money.MoneyRepository
import core.repository.player.PlayerStatusRepository
import core.usecase.changetomap.ChangeToMapUseCase
import data.item.tool.ToolRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.FinishCommand
import gamescreen.battle.usecase.getdroptool.GetDropToolUseCase
import gamescreen.battle.usecase.getexp.GetExpUseCase
import gamescreen.battle.usecase.getmoney.GetMoneyUseCase
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import values.TextData

class BattleFinishViewModel : BattleChildViewModel() {

    private val changeToMapUseCase: ChangeToMapUseCase by inject()

    private val mutableTextFLow = MutableStateFlow("")
    val textFlow = mutableTextFLow.asStateFlow()

    private val getMoneyUseCase: GetMoneyUseCase by inject()
    private val moneyRepository: MoneyRepository by inject()

    private val getExpUseCase: GetExpUseCase by inject()
    private val playerStatusRepository: PlayerStatusRepository by inject()

    private val addToolUseCase: AddToolUseCase by inject()
    private val toolRepository: ToolRepository by inject()
    private val getDropToolUseCase: GetDropToolUseCase by inject()

    private var contentType: MutableStateFlow<ContentType> =
        MutableStateFlow(ContentType.None)

    init {
        CoroutineScope(Dispatchers.Default).launch {
            contentType.collect {
                when (it) {
                    ContentType.Win -> {
                        mutableTextFLow.value = "戦闘に勝利した"
                    }

                    ContentType.Lose -> {
                        mutableTextFLow.value = "戦闘に敗北した"
                    }

                    ContentType.Exp -> {
                        val exp = getExpUseCase.invoke()
                        mutableTextFLow.value = TextData.BattleFinishExp.getText(
                            exp = exp
                        )

                        CoroutineScope(Dispatchers.Default).launch {
                            playerStatusRepository.getPlayers().mapIndexed { index, it ->
                                // レベルアップの表示をしたいのならなんかしらの管理が必要
                                playerStatusRepository.setStatus(
                                    id = index,
                                    status = it.copy(
                                        exp = it.exp.copy(
                                            value = it.exp.value + exp
                                        )
                                    ),
                                )
                            }
                        }
                    }

                    ContentType.Money -> {
                        val money = getMoneyUseCase.invoke()
                        moneyRepository.addMoney(money)
                        mutableTextFLow.value = TextData
                            .BattleFinishMoney
                            .getText(
                                money = money,
                            )
                    }

                    ContentType.Tool -> {
                        val toolId = getDropToolUseCase.invoke()

                        //道具の入手がないので次の画面へ
                        if (toolId == null) {
                            goNext()
                            return@collect
                        }

                        val name = toolRepository.getItem(
                            toolId
                        ).name

                        mutableTextFLow.value = TextData
                            .BattleFinishTool
                            .getText(
                                name = name,
                            )

                        addToolUseCase.invoke(
                            toolId = toolId,
                            toolNum = 1,
                        )
                    }

                    ContentType.None -> {
                        mutableTextFLow.value = ""
                    }
                }
            }
        }
    }


    fun init(
        isWin: Boolean,
    ) {
        contentType.value = if (isWin) {
            ContentType.Win
        } else {
            ContentType.Lose
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is FinishCommand
    }

    // 使わない
    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )

    override fun goNextImpl() {
        when (contentType.value) {
            ContentType.Win -> {
                contentType.value = ContentType.Money
            }

            ContentType.Money -> {
                contentType.value = ContentType.Exp
            }

            ContentType.Exp -> {
                contentType.value = ContentType.Tool
            }

            ContentType.Tool -> {
                finishBattle()
            }

            ContentType.Lose -> {
                finishBattle()
            }

            ContentType.None -> Unit
        }
    }

    private fun finishBattle() {
        changeToMapUseCase.invoke()
        contentType.value = ContentType.None
    }
}
