package gamescreen.map.usecase.setplayercenter

interface SetPlayerCenterUseCase {

    /**
     * プレイヤーを中心に移動する
     */
    suspend operator fun invoke()
}
