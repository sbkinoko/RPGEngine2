package core.domain.status

sealed class ConditionType {
    class Paralysis(
        val probability: Int = 50,
        val cure: Int = 50,
    ) : ConditionType()
}
