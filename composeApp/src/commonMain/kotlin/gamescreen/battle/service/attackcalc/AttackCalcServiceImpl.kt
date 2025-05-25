package gamescreen.battle.service.attackcalc

import core.domain.status.StatusData

class AttackCalcServiceImpl : AttackCalcService {
    // todo ダメージのパターンを追加する
    // 固定ダメージ
    // 割合ダメージ
    // スキルによる倍率変更
    override fun invoke(
        attacker: StatusData,
        attacked: StatusData,
    ): StatusData {
        return attacked.decHP(
            kotlin.math.max(
                attacker.atk.value - attacked.def.value,
                1,
            )
        )
    }
}
