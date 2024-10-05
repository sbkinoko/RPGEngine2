package core.domain.item

import core.domain.status.Status

enum class TargetType {
    ACTIVE {
        override fun canSelect(status: Status): Boolean {
            return status.isActive
        }
    },

    INACTIVE {
        override fun canSelect(status: Status): Boolean {
            return status.isActive.not()
        }
    }

    ;

    /**
     * 対象のstatusが選択可能かどうかを返す
     */
    abstract fun canSelect(status: Status): Boolean
}
