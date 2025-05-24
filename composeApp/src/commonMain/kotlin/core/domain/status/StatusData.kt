package core.domain.status

import core.domain.status.param.statusParameter.ParameterType
import core.domain.status.param.statusParameter.StatusParameter
import core.domain.status.param.statusParameterWithMax.HP
import core.domain.status.param.statusParameterWithMax.MP

private val dummyHP
    get() = HP(
        0, 0,
    )

private val dummyMP
    get() = MP(
        0, 0
    )

data class StatusData(
    val name: String,
    val hp: HP = dummyHP,
    val mp: MP = dummyMP,
    val speed: StatusParameter<ParameterType.SPD> = StatusParameter(0),
    val atk: StatusParameter<ParameterType.ATK> = StatusParameter(0),
    val def: StatusParameter<ParameterType.DEF> = StatusParameter(0),
    val conditionList: List<ConditionType> = emptyList(),
) {

    val isActive: Boolean
        get() {
            return 0 < hp.value
        }

    fun updateName(name: String): StatusData {
        return this.copy(
            name = name,
        )
    }

    fun setHP(
        value: Int = hp.value,
        max: Int = hp.max,
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
        value: Int = hp.value,
        max: Int = hp.max,
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
            hp = hp.incMax(
                amount = statusIncrease.hp,
            ),
            mp = mp.incMax(
                amount = statusIncrease.mp,
            ),
            atk = atk.inc(statusIncrease.atk),
            def = def.inc(statusIncrease.def),
            speed = speed.inc(statusIncrease.speed),
        )
    }

}
