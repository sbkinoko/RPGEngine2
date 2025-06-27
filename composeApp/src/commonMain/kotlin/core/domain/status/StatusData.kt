package core.domain.status

import core.domain.status.param.ParameterType
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax

// todo statusDataにTを持たせて確認する
data class StatusData(
    val name: String,
    val hp: StatusParameterWithMax<ParameterType.HP> =
        StatusParameterWithMax(
            0, 0,
        ),
    val mp: StatusParameterWithMax<ParameterType.MP> =
        StatusParameterWithMax(
            0, 0,
        ),
    val speed: StatusParameter<ParameterType.SPD> =
        StatusParameter(0),
    val atk: StatusParameter<ParameterType.ATK> =
        StatusParameter(0),
    val def: StatusParameter<ParameterType.DEF> =
        StatusParameter(0),
    val conditionList: List<ConditionType> =
        emptyList(),
) {

    val isActive: Boolean
        get() {
            return 0 < hp.point
        }

    fun setHP(
        value: Int = hp.point,
        max: Int = hp.maxPoint,
    ): StatusData {
        return this.copy(
            hp = hp.set(
                value = value,
                maxValue = max,
            ),
        )
    }

    fun decHP(amount: Int): StatusData {
        return this.copy(
            hp = hp.dec(amount),
        )
    }

    fun incHP(amount: Int): StatusData {
        return this.copy(
            hp = hp.inc(amount),
        )
    }

    fun setMP(
        value: Int = mp.point,
        max: Int = mp.maxPoint,
    ): StatusData {
        return this.copy(
            mp = mp.set(
                value = value,
                maxValue = max,
            ),
        )
    }

    fun decMP(amount: Int): StatusData {
        return this.copy(
            mp = mp.dec(amount),
        )
    }

    fun incMP(amount: Int): StatusData {
        return this.copy(
            mp = mp.inc(amount),
        )
    }

    fun updateConditionList(conditionList: List<ConditionType>): StatusData {
        return copy(
            conditionList = conditionList,
        )
    }

    fun addConditionType(conditionType: ConditionType): StatusData {
        return this.copy(
            conditionList = conditionList + conditionType,
        )
    }

    /**
     * レベルアップ時の全能力アップ処理
     */
    fun incStatus(
        statusIncrease: StatusIncrease,
    ): StatusData {
        return copy(
            hp = hp.incMax(statusIncrease.hp),
            mp = mp.incMax(statusIncrease.mp),
            atk = atk.inc(statusIncrease.atk),
            def = def.inc(statusIncrease.def),
            speed = speed.inc(statusIncrease.speed),
        )
    }

    /**
     * 装備を外したときにステータスを下げる
     */
    fun decStatus(
        statusIncrease: StatusIncrease,
    ): StatusData {
        return copy(
            hp = hp.decMax(statusIncrease.hp),
            mp = mp.decMax(statusIncrease.mp),
            atk = atk.dec(statusIncrease.atk),
            def = def.dec(statusIncrease.def),
            speed = speed.dec(statusIncrease.speed),
        )
    }

    fun reduceBuf(): StatusData {
        return copy(
            atk = atk.reduceBuf(),
            def = def.reduceBuf(),
            speed = speed.reduceBuf(),
        )
    }
}
