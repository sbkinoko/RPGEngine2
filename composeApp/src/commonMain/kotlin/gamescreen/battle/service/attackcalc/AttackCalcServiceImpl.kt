package gamescreen.battle.service.attackcalc

import core.domain.item.DamageType
import core.domain.status.StatusData
import kotlin.math.max

class AttackCalcServiceImpl : AttackCalcService {

    override fun invoke(
        attacker: StatusData,
        attacked: StatusData,
        damageType: DamageType,
    ): StatusData {
        return when (damageType) {
            is DamageType.AtkMultiple -> attacked.decHP(
                max(
                    // 攻撃*倍率 - 防御力
                    attacker.atk.value * damageType.rate - attacked.def.value,
                    // ダメージは0以下にならない
                    1,
                )
            )

            is DamageType.Rate -> attacked.decHP(
                // hpを割合で減らす
                amount = attacked.hp.point * damageType.rate / 100
            )

            is DamageType.Fixed -> attacked.decHP(
                // hpを被攻撃者のパラメータによらず減らす
                amount = damageType.amount,
            )
        }
    }
}
