package core.domain.item

sealed class DamageType {
    class Multiple(
        val rate: Int,
    ) : DamageType()

    /**
     * 現在HPに大して割合ダメージ
     * @param rate 百分率
     */
    class Rate(
        val rate: Int,
    ) : DamageType()

    class Fixed(
        val amount: Int,
    ) : DamageType()
}
