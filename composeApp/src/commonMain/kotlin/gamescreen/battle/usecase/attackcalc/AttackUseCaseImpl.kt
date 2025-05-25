package gamescreen.battle.usecase.attackcalc

import core.domain.status.StatusData

class AttackUseCaseImpl : AttackCalcUseCase {
    // todo ダメージのパターンを追加する
    // 固定ダメージ
    // 割合ダメージ
    // スキルによる倍率変更
    override fun invoke(
        attacker: StatusData,
        attacked: StatusData,
    ): StatusData {
        return attacked.decHP(
            attacker.atk.value - attacked.def.value,
        )
    }
}
