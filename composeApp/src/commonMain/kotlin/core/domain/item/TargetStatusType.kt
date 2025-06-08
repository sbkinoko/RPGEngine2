package core.domain.item

import core.domain.status.StatusData

enum class TargetStatusType {
    ACTIVE {
        override fun canSelect(status: StatusData): Boolean {
            return status.isActive
        }
    },

    INACTIVE {
        override fun canSelect(status: StatusData): Boolean {
            return status.isActive.not()
        }
    }

    ;

    /**
     * 対象のstatusが選択可能かどうかを返す
     */
    abstract fun canSelect(status: StatusData): Boolean
}
