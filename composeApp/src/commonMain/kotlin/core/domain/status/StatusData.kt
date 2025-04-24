package core.domain.status

import core.domain.status.param.HP
import core.domain.status.param.MP

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

    fun setMP(amount: Int): StatusData {
        return this.copy(
            mp = mp.copy(
                value = amount,
            )
        )
    }

    fun decMP(amount: Int): StatusData {
        return this.copy(
            mp = mp.copy(
                value = mp.value - amount
            )
        )
    }

    fun incMP(amount: Int): StatusData {
        return this.copy(
            mp = mp.copy(
                value = mp.value + amount
            )
        )
    }

    fun setHP(amount: Int): StatusData {
        return this.copy(
            hp = hp.copy(
                value = amount,
            )
        )
    }

    fun decHP(amount: Int): StatusData {
        return this.copy(
            hp = hp.copy(
                value = hp.value - amount
            )
        )
    }

    fun incHP(amount: Int): StatusData {
        return this.copy(
            hp = hp.copy(
                value = hp.value + amount
            )
        )
    }
}
