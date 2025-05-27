package gamescreen.battle.service.attackcalc

import core.domain.item.DamageType
import core.domain.status.StatusData
import kotlin.math.max

class AttackCalcServiceImpl : AttackCalcService {
    // todo ダメージのパターンを追加する
    // 固定ダメージ
    override fun invoke(
        attacker: StatusData,
        attacked: StatusData,
        damageType: DamageType,
    ): StatusData {
        return when (damageType) {
            is DamageType.Multiple -> attacked.decHP(
                max(
                    attacker.atk.value * damageType.rate - attacked.def.value,
                    1,
                )
            )

            is DamageType.Rate -> attacked.decHP(
                amount = attacked.hp.point * damageType.rate / 100
            )

            is DamageType.Fixed -> attacked.decHP(
                amount = damageType.amount,
            )
        }
    }
}
