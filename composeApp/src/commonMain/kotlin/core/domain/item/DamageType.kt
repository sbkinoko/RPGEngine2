package core.domain.item

sealed class DamageType {
    class Multiple(
        val rate: Int,
    ) : DamageType()
}
