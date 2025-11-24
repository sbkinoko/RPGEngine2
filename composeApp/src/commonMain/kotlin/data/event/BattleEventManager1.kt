package data.event

import common.DefaultScope
import core.domain.BattleEventCallback
import core.usecase.heal.MaxHealUseCase
import data.repository.monster.MonsterRepository
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.map.usecase.battlestart.StartBattleUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.launch

class BattleEventManager1(
    private val textRepository: TextRepository,
    private val maxHealUseCase: MaxHealUseCase,

    private val startBattleUseCase: StartBattleUseCase,
    private val monsterRepository: MonsterRepository,
) : AbstractEventManager<BattleEventKey>() {

    override val flgName: String
        get() = "battle_event"

    override fun callEvent(
        key: BattleEventKey,
    ) {
        val battleCallBack = BattleEventCallback(
            winCallback = {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "おめでとう",
                        callBack = { updateFlg(1) },
                    )
                )
            },
            loseCallback = {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "また挑戦してね",
                        callBack = {
                            updateFlg(1)
                            DefaultScope.launch {
                                maxHealUseCase.invoke()
                            }
                        }
                    ),
                )
            },
            escapeCallback = {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "逃げるのも時には大事だね",
                        callBack = { updateFlg(1) },
                    )
                )
            },
        )

        val talkData = listOf(
            TextBoxData(
                text = if (eventFlag.value == 0) {
                    "戦い"
                } else {
                    "再戦"
                },
                callBack = {
                    startBattleUseCase.invoke(
                        monsterList = List(5) {
                            monsterRepository.getMonster(0)
                        },
                        backgroundType = BattleBackgroundType.Event,
                        battleEventCallback = battleCallBack,
                    )
                },
            ),
        )
        textRepository.push(
            talkData,
        )
    }
}
