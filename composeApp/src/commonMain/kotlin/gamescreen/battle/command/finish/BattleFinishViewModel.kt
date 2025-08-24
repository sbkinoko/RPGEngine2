package gamescreen.battle.command.finish

import common.DefaultScope
import core.domain.BattleResult
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.memory.money.MoneyRepository
import core.usecase.changetomap.ChangeToMapUseCase
import data.repository.item.tool.ToolId
import data.repository.item.tool.ToolRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.FinishCommand
import gamescreen.battle.usecase.addexp.AddExpUseCase
import gamescreen.battle.usecase.getdroptool.GetDropToolUseCase
import gamescreen.battle.usecase.getexp.GetExpUseCase
import gamescreen.battle.usecase.getmoney.GetMoneyUseCase
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.qualifierAddToolUseCase
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import values.TextData

class BattleFinishViewModel(
    private val eventRepository: core.repository.memory.event.EventRepository,
) : BattleChildViewModel<Int>() {
    override var selectCore: SelectCore<Int> = SelectCoreInt(
        // 使わない
        SelectManager(
            width = 2,
            itemNum = 2,
        ),
    )

    private val changeToMapUseCase: ChangeToMapUseCase by inject()

    private val mutableTextFLow = MutableStateFlow("")
    val textFlow = mutableTextFLow.asStateFlow()

    private val getMoneyUseCase: GetMoneyUseCase by inject()
    private val moneyRepository: MoneyRepository by inject()

    private val getExpUseCase: GetExpUseCase by inject()

    private val addToolUseCase: AddToolUseCase<ToolId> by inject(
        qualifier = qualifierAddToolUseCase
    )
    private val toolRepository: ToolRepository by inject()
    private val getDropToolUseCase: GetDropToolUseCase by inject()

    private var contentType: MutableStateFlow<ContentType> =
        MutableStateFlow(ContentType.None)

    private var battleResult: BattleResult = BattleResult.None

    private val dropItemList: MutableStateFlow<List<ToolId>> =
        MutableStateFlow(listOf())

    private val addExpUseCase: AddExpUseCase by inject()
    private var levelUpList: List<String> = mutableListOf()
    private val mutableLevelUpListStateFlow: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())

    init {
        DefaultScope.launch {
            contentType.collect {
                when (it) {
                    ContentType.Win -> {
                        mutableTextFLow.value = "戦闘に勝利した"
                    }

                    ContentType.Lose -> {
                        mutableTextFLow.value = "戦闘に敗北した"
                    }

                    ContentType.Escape -> {
                        mutableTextFLow.value = "戦闘から逃げ出した"
                    }

                    ContentType.Exp -> {
                        val exp = getExpUseCase.invoke()
                        mutableTextFLow.value = TextData.BattleFinishExp.getText(
                            exp = exp
                        )

                        DefaultScope.launch {
                            levelUpList = addExpUseCase.invoke(
                                exp = exp,
                            )
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
                        val dropToolList = getDropToolUseCase.invoke()
                        dropItemList.value = dropToolList
                        tryNextForTool()
                    }

                    ContentType.LevelUp -> {
                        mutableLevelUpListStateFlow.value = levelUpList.toList()
                        tryNextForLevelUp()
                    }

                    ContentType.None -> {
                        mutableTextFLow.value = ""
                    }
                }
            }
        }

        DefaultScope.launch {
            dropItemList.collect {
                //道具画面じゃないので何もしない
                if (contentType.value != ContentType.Tool) {
                    return@collect
                }

                // 次の画面に遷移するのであれば何もしない
                if (tryNextForTool()) {
                    return@collect
                }

                //最初の道具の取得処理
                val toolId = it.first()
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
        }

        DefaultScope.launch {
            mutableLevelUpListStateFlow.collect {
                if (contentType.value != ContentType.LevelUp) {
                    return@collect
                }

                if (tryNextForLevelUp()) {
                    return@collect
                }

                val name = it.first()
                mutableTextFLow.value = TextData
                    .BattleFinishLevelUp
                    .getText(
                        name = name,
                    )
            }
        }
    }

    fun init(
        battleResult: BattleResult,
    ) {
        this.battleResult = battleResult
        contentType.value = when (battleResult) {
            BattleResult.Win -> ContentType.Win
            BattleResult.Lose -> ContentType.Lose
            BattleResult.Escape -> ContentType.Escape
            BattleResult.None -> ContentType.None
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is FinishCommand
    }


    override fun goNextImpl() {
        when (contentType.value) {
            ContentType.Win -> {
                contentType.value = ContentType.Exp
            }

            ContentType.Exp -> {
                contentType.value = ContentType.LevelUp
            }

            ContentType.LevelUp -> {
                mutableLevelUpListStateFlow.value =
                    mutableLevelUpListStateFlow
                        .value
                        .drop(1)
            }

            ContentType.Money -> {
                contentType.value = ContentType.Tool
            }

            ContentType.Tool -> {
                // 最初の道具は削除
                dropItemList.value = dropItemList.value.drop(1)
            }

            ContentType.Lose -> {
                finishBattle()
            }

            ContentType.Escape -> {
                finishBattle()
            }

            ContentType.None -> Unit
        }
    }

    /**
     * 道具から次の画面に進むかどうかの関数
     */
    private fun tryNextForTool(): Boolean {
        // 道具が空なら次の処理へ
        if (dropItemList.value.isEmpty()) {
            finishBattle()
            return true
        } else {
            return false
        }
    }

    /**
     * レベルアップから次の画面に進むかどうかの確認
     */
    private fun tryNextForLevelUp(): Boolean {
        if (mutableLevelUpListStateFlow.value.isEmpty()) {
            contentType.value = ContentType.Money
            return true
        } else {
            return false
        }
    }

    private fun finishBattle() {
        changeToMapUseCase.invoke()
        contentType.value = ContentType.None
        eventRepository.setResult(
            battleResult
        )
    }

    override fun pressB() {
        // NOP
    }
}
