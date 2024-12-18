package gamescreen.battle.command.finish

import core.repository.money.MoneyRepository
import core.repository.player.PlayerStatusRepository
import core.usecase.changetomap.ChangeToMapUseCase
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.FinishCommand
import gamescreen.battle.usecase.getExp.GetExpUseCase
import gamescreen.battle.usecase.getmoney.GetMoneyUseCase
import gamescreen.menu.domain.SelectManager
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

    private var contentType: ContentType = ContentType.None

    fun init(
        isWin: Boolean,
    ) {
        if (isWin) {
            mutableTextFLow.value = "戦闘に勝利した"
            contentType = ContentType.Win
        } else {
            mutableTextFLow.value = "戦闘に敗北した"
            contentType = ContentType.Lose
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
        when (contentType) {
            ContentType.Win -> {
                val money = getMoneyUseCase.invoke()
                moneyRepository.addMoney(money)
                mutableTextFLow.value = TextData
                    .BattleFinishMoney
                    .getText(
                        money = money,
                    )
                contentType = ContentType.Money

            }

            ContentType.Money -> {
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

                contentType = ContentType.Exp
            }

            ContentType.Exp -> {
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
        mutableTextFLow.value = ""
        contentType = ContentType.None
    }
}
