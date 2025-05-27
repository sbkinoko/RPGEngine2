package core.domain.item

sealed class DamageType {
    /**
     * 攻撃力に依存した倍率ダメージ
     * @param rate  攻撃力の乗算率
     */
    class AtkMultiple(
        val rate: Int,
    ) : DamageType()

    /**
     * 現在HPに大して割合ダメージ
     * @param rate 百分率
     */
    class Rate(
        val rate: Int,
    ) : DamageType()

    /**
     * 敵の能力に依存しない固定ダメージ
     * @param amount ダメージ量
     */
    class Fixed(
        val amount: Int,
    ) : DamageType()
}
