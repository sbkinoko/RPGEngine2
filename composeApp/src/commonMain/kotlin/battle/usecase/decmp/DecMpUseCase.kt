package battle.usecase.decmp

interface DecMpUseCase {

    /**
     * 対象のMPを減らす
     *
     * @param id ID,リストの何番目か
     * @param amount MPを減らす量
     */
    operator fun invoke(
        id: Int,
        amount: Int,
    )
}
