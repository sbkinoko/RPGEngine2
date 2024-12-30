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

    private val dropItemList: MutableStateFlow<List<Int>> =
        MutableStateFlow(listOf())

    private var levelUpList: MutableList<String> = mutableListOf()
    private val mutableLevelUpListStateFlow: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())

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
                            levelUpList = mutableListOf()
                            playerStatusRepository.getPlayers().mapIndexed { index, it ->
                                //経験値の加算
                                val after = it.copy(
                                    exp = it.exp.copy(
                                        value = it.exp.value + exp
                                    )
                                )

                                //保存
                                playerStatusRepository.setStatus(
                                    id = index,
                                    status = after,
                                )

                                // レベルが上がっていたら表示用リストに追加
                                if (it.exp.level != after.exp.level) {
                                    levelUpList.add(it.name)
                                }
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

        CoroutineScope(Dispatchers.Default).launch {
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

        CoroutineScope(Dispatchers.Default).launch {
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
    }

    override fun pressB() {
        // NOP
    }
}
