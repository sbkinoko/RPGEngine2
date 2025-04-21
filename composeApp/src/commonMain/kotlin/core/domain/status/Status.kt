package core.domain.status

import core.domain.status.param.HP
import core.domain.status.param.MP

interface Status {
    var name: String

    val hp: HP
    val mp: MP

    val speed: Int

    val isActive: Boolean
        get() {
            return 0 < hp.point
        }

    val conditionList: List<ConditionType>
    fun updateConditionList(conditionList: List<ConditionType>): Status
}

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

    fun addConditionType(conditionType: ConditionType): StatusData {
        return this.copy(
            conditionList = conditionList + conditionType,
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

interface Character {
    val statusData: StatusData
}

private val dummyHP
    get() = HP(
        0, 0,
    )

private val dummyMP
    get() = MP(
        0, 0
    )
