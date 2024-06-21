package domain.common.status

import domain.common.status.param.HP
import domain.common.status.param.MP

interface Status {
    var name: String

    val hp: HP

    val mp: MP

    val isActive: Boolean
        get() {
            return 0 < hp.point
        }
}
