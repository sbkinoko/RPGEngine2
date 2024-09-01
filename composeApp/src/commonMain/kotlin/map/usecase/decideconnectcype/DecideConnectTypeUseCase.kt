package map.usecase.decideconnectcype

import map.domain.ConnectType

interface DecideConnectTypeUseCase {

    /**
     * 3*3のフィールドを受け取って接続の種類をチェックする
     */
    operator fun invoke(
        array: Array<Array<Int>>
    ): ConnectType
}
