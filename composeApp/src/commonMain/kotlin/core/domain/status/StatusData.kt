package core.domain.status

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
    override var name: String,
    override val hp: HP = dummyHP,
    override val mp: MP = dummyMP,
    override val speed: Int = 0,
    override val conditionList: List<ConditionType> = emptyList(),
) : Status {
    override fun updateConditionList(conditionList: List<ConditionType>): StatusData {
        return copy(
            conditionList = conditionList,
        )
    }

    fun updateName(name: String): StatusData {
        return this.copy(
            name = name,
        )
    }

    fun addConditionType(conditionType: ConditionType): StatusData {
        return this.copy(
            conditionList = conditionList + conditionType,
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

    fun incMaxMP(amount: Int): StatusData {
        return this.copy(
            mp = mp.incMax(
                amount = amount,
            )
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

    fun incMaxHP(amount: Int): StatusData {
        return this.copy(
            hp = hp.incMax(
                amount = amount,
            )
        )
    }
}
