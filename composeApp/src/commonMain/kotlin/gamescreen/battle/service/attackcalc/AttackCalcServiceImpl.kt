package gamescreen.battle.service.attackcalc

import core.domain.item.DamageType
import core.domain.status.StatusData

class AttackCalcServiceImpl : AttackCalcService {
    // todo ダメージのパターンを追加する
    // 固定ダメージ
    // 割合ダメージ
    override fun invoke(
        attacker: StatusData,
        attacked: StatusData,
        damageType: DamageType,
    ): StatusData {
        return when (damageType) {
            is DamageType.Multiple -> attacked.decHP(
                kotlin.math.max(
                    attacker.atk.value * damageType.rate - attacked.def.value,
                    1,
                )
            )
        }
    }
}
