package core.domain.item

sealed class CostType {
    data class MP(
        val needMP: Int,
    ) : CostType()
}
