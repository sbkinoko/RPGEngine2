package main.status

import main.status.param.HP
import main.status.param.MP

interface Status {
    var name: String

    val hp: HP

    val mp: MP

    val isActive: Boolean
        get() {
            return 0 < hp.point
        }
}
