package core.domain.item

sealed class CostType {
    data class MP(
        val needMP: Int,
    ) : CostType()

    data object Consume : CostType()
    data object NotConsume : CostType()
}
