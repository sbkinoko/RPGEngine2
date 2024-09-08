package battle.service.attack

interface DecHpService<T> {

    /**
     *  攻撃をして、攻撃後のステータスを返す
     */
    fun attack(
        target: Int,
        damage: Int,
        status: T,
    ): T
}
