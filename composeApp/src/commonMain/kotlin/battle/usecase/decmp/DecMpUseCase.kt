package battle.usecase.decmp

interface DecMpUseCase {

    /**
     * 対象のMPを減らす
     *
     * @param playerId プレイヤーID
     * @param amount MPを減らす量
     */
    operator fun invoke(
        playerId: Int,
        amount: Int,
    )
}
