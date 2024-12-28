package gamescreen.battle.usecase.getdroptool

interface GetDropToolUseCase {

    /**
     * 戦闘終了時のドロップアイテムを手に入れる処理
     */
    operator fun invoke(): Int?
}
