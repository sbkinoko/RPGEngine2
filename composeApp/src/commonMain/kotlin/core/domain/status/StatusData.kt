package core.domain.status

import core.domain.status.param.ParameterType
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax

// todo statusDataにTを持たせて確認する
data class StatusData<T : StatusType>(
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

    fun updateName(name: String): StatusData<T> {
        return this.copy(
            name = name,
        )
    }

    fun setHP(
        value: Int = hp.point,
        max: Int = hp.maxPoint,
    ): StatusData<T> {
        return this.copy(
            hp = hp.set(
                value = value,
                maxValue = max,
            ),
        )
    }

    fun decHP(amount: Int): StatusData<T> {
        return this.copy(
            hp = hp.dec(amount),
        )
    }

    fun incHP(amount: Int): StatusData<T> {
        return this.copy(
            hp = hp.inc(amount),
        )
    }

    fun setMP(
        value: Int = hp.point,
        max: Int = hp.maxPoint,
    ): StatusData<T> {
        return this.copy(
            mp = mp.set(
                value = value,
                maxValue = max,
            ),
        )
    }

    fun decMP(amount: Int): StatusData<T> {
        return this.copy(
            mp = mp.dec(amount),
        )
    }

    fun incMP(amount: Int): StatusData<T> {
        return this.copy(
            mp = mp.inc(amount),
        )
    }

    fun updateConditionList(conditionList: List<ConditionType>): StatusData<T> {
        return copy(
            conditionList = conditionList,
        )
    }

    fun addConditionType(conditionType: ConditionType): StatusData<T> {
        return this.copy(
            conditionList = conditionList + conditionType,
        )
    }

    /**
     * レベルアップ時の全能力アップ処理
     */
    fun incStatus(
        statusIncrease: StatusIncrease,
    ): StatusData<T> {
        return copy(
            hp = hp.incMax(statusIncrease.hp),
            mp = mp.incMax(statusIncrease.mp),
            atk = atk.inc(statusIncrease.atk),
            def = def.inc(statusIncrease.def),
            speed = speed.inc(statusIncrease.speed),
        )
    }

    fun reduceBuf(): StatusData<T> {
        return copy(
            atk = atk.reduceBuf(),
            def = def.reduceBuf(),
            speed = speed.reduceBuf(),
        )
    }
}
