package core.domain.item

/**
 * 回復itemに必要な変数
 */
interface Heal {
    /**
     * 回復量
     */
    val healAmount: Int

    /**
     * 回復対象にとれる状態
     */
    val targetType: TargetType
}
